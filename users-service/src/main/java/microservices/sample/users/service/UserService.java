package microservices.sample.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import microservices.sample.users.model.User;
import microservices.sample.users.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        // Save object to database
        this.userRepository.save(user);
    }

    public void delete(User user) {
        // Delete object from database
        this.userRepository.delete(user);
    }
    
}
