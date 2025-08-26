package demo.service;

import demo.bean.Sight;
import org.springframework.stereotype.Service;
import demo.repository.SightRepository;

import java.util.List;

@Service
public class SightService {

    private final SightRepository sightRepository;

    public SightService(SightRepository sightRepository) {
        this.sightRepository = sightRepository;
    }

    public List<Sight> getSightsByZone(String zone) {
        return sightRepository.findByZone(zone);
    }

    public void saveAll(Iterable<Sight> sights) {
        sightRepository.saveAll(sights);
    }
}