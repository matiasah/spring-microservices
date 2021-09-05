package microservices.sample.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import microservices.sample.users.model.User;
import microservices.sample.users.repository.UserRepository;

/**
 * User service.
 * 
 * @author Matías Hermosilla
 * @since 05-09-2021
 */
@Service
public class UserService {

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a user.
     * 
     * @param user User object.
     */
    public void save(User user) {
        // Save object to database
        this.userRepository.save(user);
    }

    /**
     * Deletes a user.
     * 
     * @param user User object.
     */
    public void delete(User user) {
        // Delete object from database
        this.userRepository.delete(user);
    }
    
}
