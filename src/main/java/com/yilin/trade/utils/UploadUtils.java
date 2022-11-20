package com.yilin.trade.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Md5Utils;
import lombok.Data;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: UploadUtils
 * Description: TODO
 * 上传工具类
 * @author Yilin
 * @version V1.0
 * @date 2022-11-16
 */
@Data
public class UploadUtils {

    private static  String secretId = "敏感数据";

    private static String secretKey = "敏感数据";

    private static String bucket = "敏感数据";

    private static String bucketName = "敏感数据";

    private static String path ="敏感数据";

    private static String qianzui="敏感数据";



    public static String upload(MultipartFile multipartFile)  {

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(bucket); // ap-shanghai 表示上海
        ClientConfig clientConfig = new ClientConfig(region);


        String filename = multipartFile.getOriginalFilename();
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        //4.文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] fileNames= filename.split("\\.");
        String name = fileNames[0]+ sdf.format(new Date())+ filename.substring(filename.lastIndexOf("."), filename.length());
        //5.文件大小,使用文件流必须指定大小
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = qianzui + name;
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucketName, key, multipartFile.getInputStream(),objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 执行上传方法
        cosClient.putObject(putObjectRequest);
        // 返回线上地址
        return path + key;
    }









}
