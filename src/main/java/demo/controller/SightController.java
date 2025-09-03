package demo.controller;

import demo.bean.Sight;
import demo.service.SightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SightAPI")
public class SightController {

    private final SightService sightService;

    // 建構子注入 Service
    public SightController(SightService sightService) {
        this.sightService = sightService;
    }

    // 例如：http://localhost:8080/SightAPI?zone=七堵
    @GetMapping
    public List<Sight> getSights(@RequestParam String zone) {
        return sightService.getSightsByZone(zone);
    }
}
