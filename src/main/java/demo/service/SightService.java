package demo.service;

import demo.bean.Sight;
import demo.model.KeelungSightsCrawler;
import java.util.Arrays;
import java.util.List;

public class SightService {
    private final KeelungSightsCrawler crawler = new KeelungSightsCrawler();

    public List<Sight> getSightsByZone(String zone) {
        return Arrays.asList(crawler.getItems(zone));
    }
}

