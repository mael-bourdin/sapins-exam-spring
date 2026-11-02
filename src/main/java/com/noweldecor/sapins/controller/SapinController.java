package com.noweldecor.sapins.controller;

import com.noweldecor.sapins.entity.Sapin;
import com.noweldecor.sapins.service.SapinService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sapin")
public class SapinController {

    private final SapinService sapinService;

    public SapinController(SapinService sapinService) {
        this.sapinService = sapinService;
    }

    @GetMapping("get")
    public Sapin getSapin(@RequestParam Long id) {
        return sapinService.getSapin(id);
    }

    @PostMapping("create")
    public long createSapin() {
        return sapinService.createSapin();
    }

}
