package com.root14.postvalidatorservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @GetMapping("/user/health")
    String health();

    //patch mapping not working. probably default client not support patch mapping.
    //todo change to another client -> httpclient??
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,value = "/user/updateInterest")
    String updateInterest(
            @RequestHeader("authenticated-user-id") String authid,
            @RequestParam("interest") String interest);
}
