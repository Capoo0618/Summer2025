package demo.app;

import demo.model.KeelungSightsCrawler;
import demo.bean.Sight;
import demo.service.SightService;
import demo.repository.SightRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Configuration
public class StartupConfig {

    private final SightRepository sightRepository;

    public StartupConfig(SightRepository sightRepository) {
        this.sightRepository = sightRepository;
    }

    @Bean
    ApplicationRunner init(SightService sightService) {
        return args -> {
            KeelungSightsCrawler crawler = new KeelungSightsCrawler();
            String[] zones = {"中山區", "信義區", "仁愛區", "中正區", "安樂區", "七堵區", "暖暖區"};

            for (String zone : zones) {
                Sight[] sights = crawler.getItems(zone);
                if (sights.length > 0) {
                    sightService.saveAll(Arrays.asList(sights));
                    System.out.println("✅ 已將 " + zone + " 景點資料寫入 MongoDB: " + sights.length + " 筆");
                } else {
                    System.out.println("⚠️ " + zone + " 無景點資料被寫入，檢查 URL 或網站結構。");
                }
            }
        };
    }

    @PreDestroy
    public void cleanup() {
        sightRepository.deleteAll();
        System.out.println("✅ 程式終止時，已清除 MongoDB 中的所有景點資料。");
    }
}