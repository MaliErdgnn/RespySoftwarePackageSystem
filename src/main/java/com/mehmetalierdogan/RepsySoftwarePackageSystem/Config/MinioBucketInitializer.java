package com.mehmetalierdogan.RepsySoftwarePackageSystem.Config;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MinioBucketInitializer {
    private final MinioClient minioClient;

    @Value("${MINIO_BUCKET}")
    private String bucketName;

    @PostConstruct
    public void init() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket olusturuldu: " + bucketName);
            } else {
                System.out.println("Bucket zaten mevcut: " + bucketName);
            }
        } catch (Exception e) {
            System.err.println("MiniO bucket check sırasında hata olustu: " + e.getMessage());
        }
    }



}
