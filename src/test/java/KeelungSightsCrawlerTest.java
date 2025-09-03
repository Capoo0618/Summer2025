import demo.bean.Sight;
import demo.model.KeelungSightsCrawler;
import java.io.IOException;

public class KeelungSightsCrawlerTest {
    public static void main(String[] args) throws IOException {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        Sight[] sights = crawler.getItems("七堵");
        for (Sight s: sights) {
            System.out.println(s);
        }
    }
}