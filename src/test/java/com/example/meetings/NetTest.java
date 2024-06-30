package com.example.meetings;

import com.example.meetings.entity.Order;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class NetTest {



    public static void main(String[] args) {

    }

    private static String fileOper(String id, String time, String roomNo, String tutor, String stus, String feekback) {
        String string = null;
        try {
            // 加载Word文档
            FileInputStream fis = new FileInputStream("aa.docx");
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
}
