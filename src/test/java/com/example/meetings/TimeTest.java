package com.example.meetings;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class TimeTest {
    public static void main(String[] args) {
        long timestampInMillis = 1710127800000L; // 注意L结尾，表示这是一个long类型的字面量

        // 将毫秒级时间戳转换为Instant对象，然后转为LocalDate
        LocalDate localDate = Instant.ofEpochMilli(timestampInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 格式化输出日期
        String formattedDate = localDate.format(formatter);

        // 打印年月日格式的日期
        System.out.println(formattedDate); // 输出格式如：2024-02-29
    }

}
