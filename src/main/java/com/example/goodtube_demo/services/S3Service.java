package com.example.goodtube_demo.services;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class S3Service implements FileService{

    public static final String BUCKET_NAME = "good-tube";
    private final AmazonS3Client awsS3Client;

    @Override
    public String upload(MultipartFile file) {

        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + "." + filenameExtension;

        var metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());

        metadata.setContentType(file.getContentType());

        try{
            awsS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        } catch (IOException e) {
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                   "An Error Occured while trying to upload the file");
        }
        //awsS3Client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicReadWrite);

        return awsS3Client.getResourceUrl(BUCKET_NAME, key);
    }
}
