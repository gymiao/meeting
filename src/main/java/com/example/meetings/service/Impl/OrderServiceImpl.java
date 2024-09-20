package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Foods;
import com.example.meetings.entity.Order;
import com.example.meetings.entity.Pic;
import com.example.meetings.entity.Room;
import com.example.meetings.mapper.OrderMapper;
import com.example.meetings.service.*;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    UserService userService;

    @Resource
    PicService picService;

    @Resource
    RoomService roomService;

    @Override
    public List<Order> getByTimeAndRoom(Room room) {
        String time = "" + new Date().getTime();
        return lambdaQuery().eq(room.getRoomId()!=null, Order::getStatus, 1).eq(room.getRoomId()!=null, Order::getRoomId,room.getRoomId()).ge(time!=null, Order::getEndTime, time).list();
    }

    @Override
    public List<Order> getAllOccupy() {
        return this.list();
    }

    @Override
    public Order getOrderId(int orderId) {
        return lambdaQuery().eq(orderId!=-1, Order::getId, orderId).one();
    }

    @Override
    public boolean addOrUpdate(Order order) {
        if(order.getId() == null) {
            return this.save(order);
        } else {
            return this.updateById(order);
        }
        // return false;
    }

    @Override
    public boolean del(Order order) {
        return this.removeById(order);
    }

    @Override
    public boolean isAvi(String a, String b, Order order) {
        /*
        同一房间号
         */
        long count = 0;
        count = count + lambdaQuery().eq(Order::getRoomId, order.getRoomId())
                .ge(Order::getStartTime, a)
                .le(Order::getStartTime, b).count();


        count = count + lambdaQuery().eq(Order::getRoomId, order.getRoomId())
                .ge( Order::getEndTime, a )
                .le( Order::getEndTime, b).count();


        count = count + lambdaQuery().eq(Order::getRoomId, order.getRoomId())
                .le(Order::getStartTime, a)
                .ge(Order::getEndTime, b).count();

        if(count == 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getByUserId(String userSsno) {
        return lambdaQuery().eq(userSsno!=null, Order::getSsno, userSsno).list();
    }

    @Override
    public List<Order> getByAdminId(String adminSsno) {
        return lambdaQuery().eq(adminSsno!=null, Order::getAdmin, adminSsno).list();
    }

    @Override
    public List<Order> getByRoomId(String roomId) {
        return lambdaQuery().eq(roomId!=null, Order::getRoomId, roomId).list();
    }

    @Override
    public boolean isExsit(Order order) {
        List<Order> orders = lambdaQuery().eq(Order::getSsno, order.getSsno()).
                eq(Order::getRoomId, order.getRoomId()).
                eq(Order::getStartTime, order.getStartTime()).
                eq(Order::getEndTime, order.getEndTime()).
                eq(Order::getAdmin, order.getAdmin()).list();
        if(orders == null || orders.size() == 0) {
            return false;
        }

        return true;
    }

    @Override
    public List<Order> summaryUser(String userSsno, String startTime, String endTime) {
        return lambdaQuery().eq(userSsno!=null, Order::getSsno, userSsno).ge(startTime!=null, Order::getStartTime, startTime).le(endTime!=null, Order::getEndTime, endTime).list();
    }

    @Override
    public List<Order> summaryAdmin(String startTime, String endTime) {
        return lambdaQuery().ge(startTime!=null, Order::getStartTime, startTime).le(endTime!=null, Order::getEndTime, endTime).list();
    }

    @Override
    public String orderSummary(String userSsno, String startTime, String endTime)  {
        int pri = userService.getUserPri(userSsno);
        List<Order> list = null;
        if(pri == 1) {
            list = query().list();
            System.out.println("管理员获取"+list.size()+"条数据");
        }
        if(pri == 0) {
            lambdaQuery().eq(userSsno != null, Order::getSsno, userSsno).ge(startTime != null, Order::getStartTime, startTime).le(endTime!=null, Order::getEndTime, endTime).list();
            System.out.println("用户获取"+list.size()+"条数据");
        }
        if(list == null || list.size() == 0) {
            return "没有相应数据";
        }

        String dir = "summary";
        String string = null;
        //  下载所有文件
        boolean flag = false;
        for(Order o : list) {
            String url = o.getWordurl();
            Integer id = o.getId();
            Long l = Long.valueOf(o.getStartTime());

            Date date = new Date(l);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);

            String subDir = dir + "/" + dateString +"_" +o.getType() + "_"  +id;
            if(url == null || url.equals("") ){
                continue;
            }
            flag = true;
            saveUrlAs(url,subDir,"GET");
//            TODO 图片
            List<Pic> byOrderId = picService.getByOrderId(id);
            for(Pic pic : byOrderId) {
                saveUrlAs(pic.getLink(),subDir,"GET");
            }
        }
        if(!flag) {
            return "没有文件";
        }
        // 压缩上传
        try {
            FileOutputStream fos1 = new FileOutputStream(new File("a.zip"));
            toZip(dir, fos1, true);
            FileInputStream lis = new FileInputStream("a.zip");
            string = uploadZip(lis, dir);
            lis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return string;
    }



    private static final int BUFFER_SIZE = 2 * 1024;
    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir 压缩文件夹路径
     * @param out 压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     * false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    // 压缩文件
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
            throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }


    private static String uploadZip(InputStream inputStream, String name) {

        String ak = "4ZSCQYV7YP45CNJGWR86";
        String sk = "Dj98q1FOU7n2C2oLbHpj7O3zUO4TJYJWDM7ncXS6";
        // 【可选】如果使用临时AK/SK和SecurityToken访问OBS，同样建议您尽量避免使用硬编码，以降低信息泄露风险。
        // 您可以通过环境变量获取访问密钥AK/SK/SecurityToken，也可以使用其他外部引入方式传入。
        // String securityToken = System.getenv("SECURITY_TOKEN");
        // endpoint填写桶所在的endpoint, 此处以华北-北京四为例，其他地区请按实际情况填写。
        // 日期+姓名+类型
        String endPoint = "obs.cn-east-3.myhuaweicloud.com";
        // 您可以通过环境变量获取endPoint，也可以使用其他外部引入方式传入。
        //String endPoint = System.getenv("ENDPOINT");

        // 创建ObsClient实例
        // 使用永久AK/SK初始化客户端
        ObsClient obsClient = new ObsClient(ak, sk,endPoint);
        // 使用临时AK/SK和SecurityToken初始化客户端
        // ObsClient obsClient = new ObsClient(ak, sk, securityToken, endPoint);
        try {
            // 上传字符串（byte数组）
            String fileName = "summary.zip";
            PutObjectResult result = obsClient.putObject("picnuaa", fileName, inputStream);
            return result.getObjectUrl();
        } catch (ObsException e) {
            System.out.println("putObject failed");
            // 请求失败,打印http状态码
            System.out.println("HTTP Code:" + e.getResponseCode());
            // 请求失败,打印服务端错误码
            System.out.println("Error Code:" + e.getErrorCode());
            // 请求失败,打印详细错误信息
            System.out.println("Error Message:" + e.getErrorMessage());
            // 请求失败,打印请求id
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("putObject failed");
            // 其他异常信息打印
            e.printStackTrace();
        }
        return null;
    }

    public static File saveUrlAs(String url,String filePath,String method){
        String[] split = url.split("/");
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        File file=new File(filePath);

        file.delete();
        //如果文件夹不存在，则创建新的的文件夹
        file.mkdirs();

        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath+split[split.length-1]);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }

        return file;

    }


    @Override
    public String outputPerson(Integer orderId) {

        Order order = this.getById(orderId);
        if(order == null) {
            return "没有该会议";
        }
        if(order.getCnts() <= 0) {
            return "该会议未订餐";
        }
        String names = order.getNames();
        Room room = roomService.getOneById(order.getRoomId());
        String url = fileOper(order.getId().toString(), order.getStartTime(), room.getRoomNo(), order.getLeader(), order.getCnts(), names);

        return url;
    }


    private static String uploadWordNew(InputStream inputStream, String Id) {
        // 您可以通过环境变量获取访问密钥AK/SK，也可以使用其他外部引入方式传入。如果使用硬编码可能会存在泄露风险。
        // 您可以登录访问管理控制台获取访问密钥AK/SK
        String ak = "4ZSCQYV7YP45CNJGWR86";
        String sk = "Dj98q1FOU7n2C2oLbHpj7O3zUO4TJYJWDM7ncXS6";
        // 【可选】如果使用临时AK/SK和SecurityToken访问OBS，同样建议您尽量避免使用硬编码，以降低信息泄露风险。
        // 您可以通过环境变量获取访问密钥AK/SK/SecurityToken，也可以使用其他外部引入方式传入。
        // String securityToken = System.getenv("SECURITY_TOKEN");
        // endpoint填写桶所在的endpoint, 此处以华北-北京四为例，其他地区请按实际情况填写。
        String endPoint = "obs.cn-east-3.myhuaweicloud.com";
        // 您可以通过环境变量获取endPoint，也可以使用其他外部引入方式传入。
        //String endPoint = System.getenv("ENDPOINT");

        // 创建ObsClient实例
        // 使用永久AK/SK初始化客户端
        ObsClient obsClient = new ObsClient(ak, sk,endPoint);
        // 使用临时AK/SK和SecurityToken初始化客户端
        // ObsClient obsClient = new ObsClient(ak, sk, securityToken, endPoint);
        try {
            // 上传字符串（byte数组）
            String fileName = Id + ".doc";
            PutObjectResult result = obsClient.putObject("picnuaa", fileName, inputStream);
            return result.getObjectUrl();
        } catch (ObsException e) {
            System.out.println("putObject failed");
            // 请求失败,打印http状态码
            System.out.println("HTTP Code:" + e.getResponseCode());
            // 请求失败,打印服务端错误码
            System.out.println("Error Code:" + e.getErrorCode());
            // 请求失败,打印详细错误信息
            System.out.println("Error Message:" + e.getErrorMessage());
            // 请求失败,打印请求id
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("putObject failed");
            // 其他异常信息打印
            e.printStackTrace();
        }
        return null;
    }

    private static String fileOper(String id, String time, String roomNo, String tutor, int cnts,String names) {
        String string = null;
        try {
            // 加载Word文档
            FileInputStream fis = new FileInputStream("aa.docx");
            XWPFDocument document = new XWPFDocument(fis);
            long timestampInMillis = Long.parseLong(time);
            LocalDate localDate = Instant.ofEpochMilli(timestampInMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // 定义日期格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 格式化输出日期
            time = localDate.format(formatter);


            // 假设我们要填充的是文档中的第一个表格
            List<XWPFTable> tables = document.getTables();
            if (!tables.isEmpty()) {
                XWPFTable table = tables.get(0); // 获取第一个表格

                // 填充表格的某个单元格，例如第一行第二列（注意：行和列的索引都是从0开始的）
                table.getRow(0).getCell(1).setText(time); // 获取第4列的单元格
                table.getRow(0).getCell(3).setText(roomNo); // 获取第4列的单元格
                table.getRow(1).getCell(1).setText(tutor);
                table.getRow(2).getCell(1).setText(String.valueOf(cnts));
                table.getRow(3).getCell(1).setText(names);
            }

            // 保存修改后的文档
            FileOutputStream out = new FileOutputStream("bb.docx");
            document.write(out);
            FileInputStream lis = new FileInputStream("bb.docx");

            string = uploadWordNew(lis, time+"--"+id);
            // 关闭资源
            out.close();
            document.close();
            fis.close();
            lis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return string;
        }
    }


}
