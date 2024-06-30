package com.example.meetings;

import com.example.meetings.entity.Order;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.FileInputStream;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MeetingsApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Hello");
    }

    public void main11(String[] args) {
        Order order = new Order();
        order.setId(1);
        try{
            String data = "<!--StartFragment--><div class=\"Section0\"  style=\"layout-grid:15.6000pt;\" ><h1 align=center  style=\"text-align:center;\" ><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;\n" +
                    "font-size:22.0000pt;mso-font-kerning:22.0000pt;\" ><font face=\"宋体\" >标题</font></span></b><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;\n" +
                    "font-size:22.0000pt;mso-font-kerning:22.0000pt;\" ><o:p></o:p></span></b></h1><h2><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:黑体;mso-ascii-font-family:Arial;\n" +
                    "mso-hansi-font-family:Arial;mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;\n" +
                    "font-size:16.0000pt;mso-font-kerning:1.0000pt;\" ><font face=\"黑体\" >一、段落</font><font face=\"Arial\" >1</font></span></b><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:Arial;mso-fareast-font-family:黑体;\n" +
                    "mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;font-size:16.0000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></b></h2><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >哒哒</font></span><b><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';color:rgb(0,0,255);\n" +
                    "font-weight:bold;font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >哒哒哒</font></span></b><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >哒</font></span><span style=\"mso-spacerun:'yes';font-family:Calibri;mso-fareast-font-family:宋体;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p><h3><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;\n" +
                    "font-size:16.0000pt;mso-font-kerning:1.0000pt;\" ><font face=\"Calibri\" >1.1</font><font face=\"宋体\" >、表格</font></span></b><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:Calibri;mso-fareast-font-family:宋体;\n" +
                    "mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;font-size:16.0000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></b></h3><table class=MsoTableGrid  border=1  cellspacing=0  style=\"border-collapse:collapse;border:none;mso-border-left-alt:0.5000pt solid windowtext;\n" +
                    "mso-border-top-alt:0.5000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\n" +
                    "mso-border-insideh:0.5000pt solid windowtext;mso-border-insidev:0.5000pt solid windowtext;mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;\" ><tr><td width=142  valign=center  style=\"width:106.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >序号</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td><td width=142  valign=center  style=\"width:106.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >列</font><font face=\"Calibri\" >1</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td><td width=142  valign=center  style=\"width:106.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >列</font><font face=\"Calibri\" >2</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td><td width=142  valign=center  style=\"width:106.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"宋体\" >列</font><font face=\"Calibri\" >3</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td></tr><tr><td width=142  valign=center  style=\"width:106.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"Calibri\" >1</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td><td width=142  valign=center  style=\"width:106.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"Calibri\" >11</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td><td width=142  valign=center  style=\"width:106.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"Calibri\" >22</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td><td width=142  valign=center  style=\"width:106.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;\n" +
                    "mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;\n" +
                    "border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;\n" +
                    "mso-border-bottom-alt:0.5000pt solid windowtext;\" ><p class=MsoNormal  align=center  style=\"text-align:center;\" ><span style=\"font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><font face=\"Calibri\" >33</font></span><span style=\"font-family:Calibri;mso-fareast-font-family:宋体;mso-bidi-font-family:'Times New Roman';\n" +
                    "font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></p></td></tr></table><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:Calibri;mso-fareast-font-family:宋体;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:Calibri;mso-fareast-font-family:宋体;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:Calibri;mso-fareast-font-family:宋体;\n" +
                    "mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;mso-font-kerning:1.0000pt;\" ><o:p>&nbsp;</o:p></span></p><h2><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:黑体;mso-ascii-font-family:Arial;\n" +
                    "mso-hansi-font-family:Arial;mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;\n" +
                    "font-size:16.0000pt;mso-font-kerning:1.0000pt;\" ><font face=\"黑体\" >二、段落</font><font face=\"Arial\" >2</font></span></b><b style=\"mso-bidi-font-weight:normal\" ><span style=\"mso-spacerun:'yes';font-family:Arial;mso-fareast-font-family:黑体;\n" +
                    "mso-bidi-font-family:'Times New Roman';mso-ansi-font-weight:bold;font-size:16.0000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><o:p></o:p></span></b></h2><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal ><span style=\"mso-spacerun:'yes';font-family:宋体;mso-ascii-font-family:Calibri;\n" +
                    "mso-hansi-font-family:Calibri;mso-bidi-font-family:'Times New Roman';font-size:10.5000pt;\n" +
                    "mso-font-kerning:1.0000pt;\" ><o:p>&nbsp;</o:p></span></p></div><!--EndFragment-->";
            File file =new File(order.getId()+".txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();
            System.out.println("Done");
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main111(String[] args) {
        long mseconds = 1711096140000L;
        String str = getString(String.valueOf(mseconds));
        System.out.println(str);
    }


    private static String getString(String time) {
        Long l = Long.valueOf(time);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(l);
        String str = sdf.format(date);
        return str;
    }
//    border="0"，border-collapse=“collapse”，以及cellspacing="-1"。
    static String HTML1 = "<h1 style=\"text-align: center;\"><strong>人文与社会科学学院本科生导师制午餐会记录表</strong></h1>"+

            "<table border=\"0\" border-collapse=\"collapse\" cellspacing=\"-1\" style=\"width: 100%;\" height=\"100px\" cellspacing=\"0\" border=\"1px solid black\" border-collapse:\"collapse\">" +
            "<tbody>" +

            "<tr><th colSpan=\"1\" rowSpan=\"1\" height = \"100px\" width=\"auto\">时间</th>" +
            "<th colSpan=\"1\" rowSpan=\"1\" height = \"100px\" width=\"auto\">第一列</th>" +
            "<th colSpan=\"1\" rowSpan=\"1\" height = \"100px\" width=\"auto\">地点</th>" +
            "<th colSpan=\"1\" rowSpan=\"1\" height = \"100px\" width=\"auto\">第三列</th>" +

            "</tr>" +

            "<tr><td colspan=\"1\" rowspan=\"1\" width=\"auto\" height = \"100px\" style=\"text-align: center;\">导师姓名</td>" +
            "<td colspan=\"1\" rowspan=\"1\" width=\"auto\" height = \"100px\" colspan=\"3\"  style=\"text-align: center;\">11</td>" +


            "</tr>" +

            "<tr><td colspan=\"1\" rowspan=\"1\" width=\"auto\" height = \"100px\" style=\"text-align: center;\">参会学生</td>" +
            "<td colspan=\"1\" rowspan=\"1\" width=\"auto\" height = \"100px\" colspan=\"3\" style=\"text-align: center;\">11</td>" +


            "</tr>" +

            "<tr><td colspan=\"1\" rowspan=\"1\" width=\"auto\" height = \"1000px\" rowspan=\"20\" style=\"text-align: center;\">会议内容</td>" +
            "<td colspan=\"1\" rowspan=\"1\" width=\"100px\" height = \"1000px\" colspan=\"3\" rowspan=\"20\" style=\"text-align: center;\">1、专业学习与职业规划\n" +
            "2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" +
            "            \"2、课题研究思路与开展1、专业学习与职业规划\\n\" +\n" + "<br></br>" +
            "            \"2、课题研究思路与开展</td>" +



            "</tbody></table><p><br></p>";


    public static void main(String[] args) {
        fileOper();
    }

    private static void fileOper() {
        try {
            // 加载Word文档
            FileInputStream fis = new FileInputStream("a.docx");
            XWPFDocument document = new XWPFDocument(fis);

            // 假设我们要填充的是文档中的第一个表格
            List<XWPFTable> tables = document.getTables();
            if (!tables.isEmpty()) {
                XWPFTable table = tables.get(0); // 获取第一个表格

                // 填充表格的某个单元格，例如第一行第二列（注意：行和列的索引都是从0开始的）
                table.getRow(0).getCell(1).setText("time"); // 获取第4列的单元格
                table.getRow(0).getCell(3).setText("roomNo"); // 获取第4列的单元格
                table.getRow(1).getCell(1).setText("tutor");
                table.getRow(2).getCell(1).setText("stus");
                table.getRow(3).getCell(1).setText("feekback");
            }

            // 保存修改后的文档
            FileOutputStream out = new FileOutputStream("b.docx");
            document.write(out);
            FileInputStream lis = new FileInputStream("b.docx");
            uploadWord(lis);
            // 关闭资源
            out.close();
            document.close();
            fis.close();
            lis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String uploadWord(InputStream inputStream) {
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
            String fileName = "xxxx.doc";
            PutObjectResult result = obsClient.putObject("picnuaa", fileName, inputStream);
            System.out.println(result.getObjectUrl());
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
