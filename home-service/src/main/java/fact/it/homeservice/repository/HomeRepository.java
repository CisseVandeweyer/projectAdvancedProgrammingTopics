package fact.it.homeservice.repository;

import fact.it.homeservice.model.Home;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HomeRepository extends MongoRepository<Home, String> {

}
