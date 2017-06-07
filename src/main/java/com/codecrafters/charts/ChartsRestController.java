package com.codecrafters.charts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
class ChartsRestController {

    private final ChartsService chartsService;

    @Autowired
    public ChartsRestController(final ChartsService chartsService) {
        this.chartsService = chartsService;
    }

    @RequestMapping("/charts")
    public Charts getCharts() {
        return chartsService.getCharts();
    }
}
