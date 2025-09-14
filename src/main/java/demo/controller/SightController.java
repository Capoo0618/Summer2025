package demo.controller;

import demo.bean.Sight;
import demo.service.SightService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<List<Sight>> getSights(@RequestParam String zone) {
        List<Sight> sights = sightService.getSightsByZone(zone);

        if (sights == null || sights.isEmpty()) {
            return ResponseEntity.notFound().build(); // 回傳 404
        }

        return ResponseEntity.ok(sights); // 回傳 200 + 資料
    }
}
