package com.root14.postvalidatorservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @GetMapping("/user/health")
    String health();

    //patch mapping not working. probably default client not support patch mapping.
    //todo change to another client -> httpclient??
    @RequestMapping(method = RequestMethod.POST, value = "/user/updateInterest")
    String updateInterest(
            @RequestHeader("authenticated-user-id") String authid,
            @RequestParam("interest") String interest);
}
