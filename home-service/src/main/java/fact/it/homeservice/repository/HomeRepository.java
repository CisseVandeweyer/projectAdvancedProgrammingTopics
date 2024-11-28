package fact.it.homeservice.repository;

import fact.it.homeservice.model.Home;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface HomeRepository extends MongoRepository<Home, String> {
    @Query("{ '_id': ?0 }")
    Home findHomeById(String id);
}
