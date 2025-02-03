package com.root14.postservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service")
public interface ImageFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/image/uploadImage")
    String uploadImage(@RequestPart("file") MultipartFile multipartFile);
}
