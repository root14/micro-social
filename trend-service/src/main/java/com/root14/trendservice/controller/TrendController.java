package com.root14.trendservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trends")
public class TrendController {


    @GetMapping
    public ResponseEntity<?> getTrends(@RequestParam(defaultValue = "24h") String period) {
        return ResponseEntity.ok(period);
    }

}
