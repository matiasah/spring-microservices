package microservices.sample.clients.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import microservices.sample.clients.model.Application;

/**
 * Repository for {@link Application}
 * 
 * @author Matías Hermosilla
 * @since 04-09-2021
 */
public interface ApplicationRepository extends MongoRepository<Application, String> {
    
}
