package com.sysmei.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sysmei.service.S3Service;
import com.sysmei.service.exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3ServiceImpl implements S3Service {

  private Logger LOG = LoggerFactory.getLogger(S3ServiceImpl.class);

  @Autowired
  private AmazonS3 s3client;

  @Value("${s3.bucket}")
  private String bucketName;

  public URI uploadFile(MultipartFile multipartfile) {
    try {
      String fileName = multipartfile.getOriginalFilename();
      InputStream is = multipartfile.getInputStream();

      String contentType = multipartfile.getContentType();

      return uploadFile(is, fileName, contentType);
    } catch (IOException e) {
      throw new FileException("Erro de IO: " + e.getMessage());
    }

  }

  public URI uploadFile(InputStream is, String fileName, String contentType) {
    try {
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentType(contentType);
      LOG.info("Iniciando upload");
      s3client.putObject(bucketName, fileName, is, meta);
      LOG.info("Upload Finalizado");

      return s3client.getUrl(bucketName, fileName).toURI();
    } catch (URISyntaxException e) {
      throw new FileException("Erro ao converter URL para URI");
    }
  }

  public void deleteFile(final String keyName) {
    LOG.info("Deleting file with name= " + keyName);
    final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
    s3client.deleteObject(deleteObjectRequest);
    LOG.info("File deleted successfully.");
  }
}
