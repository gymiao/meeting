package com.example.meetings.utils;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ObsUtil {
    private static final String ak = "4ZSCQYV7YP45CNJGWR86";
    private static final String sk = "Dj98q1FOU7n2C2oLbHpj7O3zUO4TJYJWDM7ncXS6";
    private static final String endPoint = "obs.cn-east-3.myhuaweicloud.com";
    private static String bucketName = "picnuaa";

    public void downloadZip(String zipFileName, String filesPath, HttpServletResponse response) throws Exception {
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName, "UTF-8"));
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

            ObsClient obsClient = new ObsClient(ak, sk, endPoint);
            ListObjectsRequest request = new ListObjectsRequest(bucketName);
            request.setPrefix(filesPath);
            request.setMaxKeys(500);
            ObjectListing result;
            result = obsClient.listObjects(request);
            for (ObsObject obsObject : result.getObjects()) {
                String objectKey = obsObject.getObjectKey();
                if (!objectKey.endsWith("/")){
                    int index = objectKey.lastIndexOf("/");
                    String fileName = objectKey.substring(index+1);
                    out.putNextEntry(new ZipEntry(fileName));

                    InputStream in = obsObject.getObjectContent();
                    int b;
                    while ((b = in.read())!=-1){
                        out.write(b);
                    }
                }
            }
            out.flush();
        } catch (ObsException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

