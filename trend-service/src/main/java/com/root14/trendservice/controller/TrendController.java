package com.root14.trendservice.controller;

import com.root14.trendservice.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trends")
public class TrendController {

    private final TrendService trendService;

    @Autowired
    public TrendController(TrendService trendService) {
        this.trendService = trendService;
    }

    @GetMapping
    public ResponseEntity<?> getTrends(@RequestParam(defaultValue = "24h") String period) {
        return trendService.getTrend(period);
    }

}
