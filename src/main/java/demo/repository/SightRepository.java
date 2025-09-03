package demo.repository;

import demo.bean.Sight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SightRepository extends MongoRepository<Sight, String> {
    // 查詢指定區域的景點
    List<Sight> findByZone(String zone);
}
