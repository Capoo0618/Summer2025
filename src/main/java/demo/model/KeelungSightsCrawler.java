package demo.model;

import demo.bean.Sight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeelungSightsCrawler {

    public KeelungSightsCrawler() {}

    // 抓取指定區域的所有景點
    public Sight[] getItems(String zoneFilter) {
        List<Sight> sights = new ArrayList<>();

        try {
            String baseUrl = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";
            Document doc = Jsoup.connect(baseUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10000)
                    .get();

            Element guideSection = doc.getElementById("guide-point");
            if (guideSection == null) {
                System.err.println("無法找到 id=guide-point 的區塊，檢查 " + baseUrl);
                return new Sight[0];
            }

            Elements headings = guideSection.select("h4");
            for (Element heading : headings) {
                String zoneName = heading.text().trim();

                if (!zoneName.contains(zoneFilter)) continue;

                Element ul = heading.nextElementSibling();
                if (ul == null || !ul.tagName().equals("ul")) continue;

                for (Element li : ul.select("li")) {
                    String link = li.selectFirst("a") != null ? li.selectFirst("a").attr("href") : "";
                    if (link.isEmpty()) continue;

                    if (!link.startsWith("http")) {
                        link = "https://www.travelking.com.tw" + link;
                    }

                    Sight sight = fetchSightDetails(link, zoneName);
                    if (sight != null && !sight.getSightName().isEmpty() && !sight.getZone().isEmpty()) {
                        sights.add(sight);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("主頁面載入失敗: " + e.getMessage() + " for URL: ");
        }

        return sights.toArray(new Sight[0]);
    }

    // 抓取單一景點詳細資料
    private Sight fetchSightDetails(String url, String zoneName) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10000)
                    .get();

            Sight sight = new Sight();

            Element nameMeta = doc.selectFirst("meta[itemprop=name]");
            sight.setSightName(nameMeta != null ? nameMeta.attr("content").trim() : "");

            sight.setZone(zoneName);

            Element categoryElement = doc.selectFirst("span[property=rdfs:label] strong");
            sight.setCategory(categoryElement != null ? categoryElement.text().trim() : "");

            Element photoMeta = doc.selectFirst("meta[itemprop=image]");
            sight.setPhotoURL(photoMeta != null ? photoMeta.attr("content").trim() : "");

            Element descMeta = doc.selectFirst("meta[itemprop=description]");
            sight.setDescription(descMeta != null ? descMeta.attr("content").trim() : "");

            Element addrMeta = doc.selectFirst("meta[itemprop=address]");
            sight.setAddress(addrMeta != null ? addrMeta.attr("content").trim() : "");

            return !sight.getSightName().isEmpty() || !sight.getDescription().isEmpty() ? sight : null;
        } catch (IOException e) {
            System.err.println("載入景點頁面失敗: " + url + " - " + e.getMessage());
            return null;
        }
    }
}