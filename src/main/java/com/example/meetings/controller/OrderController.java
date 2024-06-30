package com.example.meetings.controller;

import com.example.meetings.entity.Key;
import com.example.meetings.entity.Order;
import com.example.meetings.entity.Pic;
import com.example.meetings.entity.User;
import com.example.meetings.service.*;
import com.example.meetings.utils.GetKey;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService occupyService;

    @Autowired
    UserService userService;

    @Autowired
    KeyService keyService;

    @Autowired
    PicService picService;

    @Resource
    RoomService roomService;

    @GetMapping("/getPics")
    public List<Pic> get(@RequestParam("orderId") Integer orderId) {
        return picService.getAll(orderId);
    }

    @GetMapping("/addPics")
    public boolean add(@RequestParam("orderId") Integer orderId, @RequestParam("link") String link) {
        return picService.add(orderId, link);
    }

    @GetMapping("/delPic")
    public boolean del(@RequestParam("id") Integer id) {
        return picService.del(id);
    }


    @GetMapping("/outputPerson")
    public String outputPerson(@RequestParam("orderId") Integer orderId){
        return occupyService.outputPerson(orderId);
    }




    @GetMapping("/getAll")
    public List<Order> getAll(@RequestParam("ssno") String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return null;
        }
        List<Order> orders = occupyService.getAllOccupy();
        for(Order o : orders) {
            if(o.getStatus() == 1) {
                long l = System.currentTimeMillis();
                String currentTime = String.valueOf(l);
                // 已过期
                if(o.getEndTime().compareTo(currentTime) <= 0) {
                    o.setStatus(-2);
                    occupyService.updateById(o);
                }
            }
        }
        return occupyService.getAllOccupy();
    }

    @GetMapping("/getByRoomId")
    public List<Order> getByRoomId(@RequestParam("roomId") String roomId) {

        return occupyService.getByRoomId(roomId);
    }

    @PostMapping("/addOrUpdate")
    public boolean addorupdate(@RequestBody Order order) {

        return occupyService.addOrUpdate(order);
    }

    /**
     * /order/del
     * @param id
     * @param ssno
     * @return
     */
    @GetMapping("/del")
    public boolean del(@RequestParam Integer id, @RequestParam String ssno) {
        // 判断occupy的id是否为空
        Order order = new Order();
        order.setId(id);
        order = occupyService.getById(order.getId());
        if(userService.getUserPri(ssno) == 1) {
            return occupyService.removeById(order);
        }
        if(!order.getSsno().equals(ssno)) {
            return false;
        }
        return occupyService.removeById(order);
    }

    private static String uploadWord(Order order) {
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
            String content = order.toString();
            String fileName = order.getId()+".doc";
            PutObjectResult result = obsClient.putObject("picnuaa", fileName, new ByteArrayInputStream(content.getBytes()));
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

    private static String fileOper(String id, String time, String roomNo, String tutor, String stus, String feekback) {
        String string = null;
        try {
            // 加载Word文档
            FileInputStream fis = new FileInputStream("a.docx");
            XWPFDocument document = new XWPFDocument(fis);

            // 假设我们要填充的是文档中的第一个表格
            List<XWPFTable> tables = document.getTables();
            if (!tables.isEmpty()) {
                XWPFTable table = tables.get(0); // 获取第一个表格

                // 填充表格的某个单元格，例如第一行第二列（注意：行和列的索引都是从0开始的）
                table.getRow(0).getCell(1).setText(time); // 获取第4列的单元格
                table.getRow(0).getCell(3).setText(roomNo); // 获取第4列的单元格
                table.getRow(1).getCell(1).setText(tutor);
                table.getRow(2).getCell(1).setText(stus);
                table.getRow(3).getCell(1).setText(feekback);
            }

            // 保存修改后的文档
            FileOutputStream out = new FileOutputStream("b.docx");
            document.write(out);
            FileInputStream lis = new FileInputStream("b.docx");
            string = uploadWordNew(lis, id);
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


    @GetMapping("/updateFeedback")
    public boolean updateFeedback(@RequestParam Integer id, @RequestParam String feedback,
                                  @RequestParam("picture") String picture, @RequestParam("daoshi")String daoshi,
                                  @RequestParam("stus")String stus) {
        Order order = new Order();
        order.setId(id);
        order = occupyService.getById(order.getId());
        if(order == null) {
            return false;
        }
        order.setFeekback(feedback);
        Long l1 = Long.valueOf(order.getStartTime());
        Date date1 = new Date(l1);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy.MM.dd");
        String roomNo = roomService.getOneById(order.getRoomId()).getRoomNo();

        // 上传word
        String url = fileOper(order.getId().toString(), sdf.format(date1), roomNo, daoshi, stus, feedback);
        order.setWordurl(url);
        order.setPicture(picture);
        order.setStatus(2);
        return occupyService.addOrUpdate(order);
    }


    @GetMapping("/getByUserId")
    public List<Order> getByUserId(@RequestParam(value = "userId") String userSsno) {
        if(userSsno == null) {
            return null;
        }
        List<Order> orders = occupyService.getByUserId(userSsno);
        for(Order o : orders) {
            if(o.getStatus() == 1) {
                long l = System.currentTimeMillis();
                String currentTime = String.valueOf(l);
                // 已过期
                if(o.getEndTime().compareTo(currentTime) <= 0) {
                    o.setStatus(-2);
                    occupyService.updateById(o);
                }
            }
        }
        return occupyService.getByUserId(userSsno);
    }

    // 获取需要审核的申请
    @GetMapping("/getByAdminId")
    public List<Order> getByAdminId(@RequestParam("ssno") String adminSsnos) {
        System.out.println("what");
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return null;
        }
        return occupyService.getByAdminId(adminSsnos);
    }

    /**
     * /order/summaryUser
     * @param ssno
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/summaryUser")
    public String summary(@RequestParam("ssno") String ssno, @RequestParam("startTime") String startTime,
                               @RequestParam("endTime") String endTime) {
        return occupyService.orderSummary(ssno, startTime, endTime);
    }


    /**
     * /order/summaryAdmin
     * @param ssno
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/summaryAdmin")
    public String summaryAdmin(@RequestParam("ssno") String ssno, @RequestParam("startTime") String startTime,
                                    @RequestParam("endTime") String endTime) {
        User u = userService.findBySsno(ssno);
        if(u == null || u.getPriority() != 1) {
            return null;
        }
        return occupyService.orderSummary(ssno, startTime, endTime);
    }



    // 修改order状态
    @PostMapping("/updateStatus")
    public String updateOrder(@RequestParam("id") int id, @RequestParam("roomId") String roomId,
                               @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
                               @RequestParam("type") int type, @RequestParam("ssno") String ssno,
                               @RequestParam("detail") String detail, @RequestParam("status") int status,
                               @RequestParam("feekback") String feekback, @RequestParam("picture") String picture,
                               @RequestParam("admin") String admin,
                               @RequestParam("adminId") String adminId) {
        Order order = new Order();
        order.setId(id);
        order.setRoomId(roomId);
        order.setStartTime(startTime);
        order.setEndTime(endTime);
        order.setType(type);
        order.setSsno(ssno);
        order.setDetail(detail);
        order.setStatus(status);
        order.setFeekback(feekback);
        order.setPicture(picture);
        order.setAdmin(admin);

        Order order1 = occupyService.getById(order.getId());
        if(order1 == null) {
            return  "不存在该预约";
        }

        if (order1.getStatus() != 0) {
            return "该预约已经审核过了";
        }

        // 管理员调用,尝试审核
        if ( userService.getUserPri(adminId) == 1 ) {
            order.setStatus(1);
            occupyService.addOrUpdate(order);
            // 申请key
            // 直接申请密码
            String pwd = GetKey.getKey(order1.getRoomId(), startTime, endTime);
            // 保存新的key
            Key key = new Key();
            key.setRoomId(order1.getRoomId());
            key.setPwd(pwd);
            key.setSsno(order1.getSsno());
            key.setStartDate(startTime);
            key.setEndDate(endTime);
            // 可以使用
            key.setStatus(1);
            keyService.addOrUpdate(key);
            return "审核成功";
        }
        return "审核失败";
    }




}
