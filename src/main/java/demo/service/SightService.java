package demo.service;

import demo.bean.Sight;
import demo.model.KeelungSightsCrawler;
import demo.repository.SightRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SightService {

    private final SightRepository sightRepository;

    public SightService(SightRepository sightRepository) {
        this.sightRepository = sightRepository;
    }

    public List<Sight> getSightsByZone(String zone) {
        List<Sight> sights = sightRepository.findByZone(zone);

        // ✅ 如果資料庫是空的，就自動補資料
        if (sights.isEmpty() && sightRepository.count() == 0) {
            System.out.println("⚠️ 資料庫為空，自動觸發爬蟲...");
            reloadAllData();  // ⬅️ 呼叫這裡定義的 method
            sights = sightRepository.findByZone(zone); // 補完再查一次
        }

        return sights;
    }

    public void saveAll(Iterable<Sight> sights) {
        sightRepository.saveAll(sights);
    }

    public void deleteAllAndReload() {
        sightRepository.deleteAll();
        System.out.println("🧹 已清空資料，開始重新爬蟲...");
        reloadAllData();
    }

    // ✅ 這就是你問的 reloadAllData() 放這裡最合理
    public void reloadAllData() {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        String[] zones = {"中山區", "信義區", "仁愛區", "中正區", "安樂區", "七堵區", "暖暖區"};

        for (String zone : zones) {
            Sight[] sights = crawler.getItems(zone);
            for (Sight sight : sights) {
                if (sight.getPhotoURL() == null || sight.getPhotoURL().isBlank()) {
                    sight.setPhotoURL("/images/no_image.jpg");
                }
                if (sight.getDescription() == null || sight.getDescription().isBlank()) {
                    sight.setDescription("暫無詳細描述");
                }
            }

            if (sights.length > 0) {
                sightRepository.saveAll(Arrays.asList(sights));
                System.out.println("✅ 已寫入 " + zone + " 資料，共 " + sights.length + " 筆");
            } else {
                System.out.println("⚠️ " + zone + " 沒有資料");
            }
        }
    }
}
