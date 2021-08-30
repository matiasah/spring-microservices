package microservices.sample.users.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import microservices.sample.users.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    
}
