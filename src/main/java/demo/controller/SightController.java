package demo.controller;

import demo.bean.Sight;
import demo.service.SightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class SightController {

    private final SightService sightService = new SightService();

    @GetMapping("/SightAPI")
    public List<Sight> getSights(@RequestParam String zone) {
        return sightService.getSightsByZone(zone);
    }
}

