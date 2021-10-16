package microservices.sample.users.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import microservices.sample.users.model.User;
import microservices.sample.users.repository.UserRepository;

/**
 * Seeder for the users service.
 * 
 * @author Matías Hermosilla
 * @since 15-10-2021
 */
@Component
public class UserSeeder {

    /**
     * The user name.
     */
    @Value("${user.name}")
    private String username;

    /**
     * The user password.
     */
    @Value("${user.password}")
    private String password;

    /**
     * The user repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Seeder for the users service.
     * 
     * @param event The event that triggered the seeder.
     */
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        
        // If the users collection is empty
        if (userRepository.count() == 0) {
            
            // Create a new user object
            User user = new User();

            // Encode the password
            String encodedPassword = passwordEncoder.encode(password);

            // Set the user's username and password
            user.setUsername(username);
            user.setPassword(encodedPassword);

            // Enable the user
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            // Save the user to the database
            userRepository.save(user);
        }
        
    }
    
}
