package microservices.sample.auth.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import microservices.sample.auth.model.User;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user
        List<User> users = this.userService.findAllByUsername(username);

        // If user list is empty
        if (users.isEmpty()) {
            // Throw exception
            throw new UsernameNotFoundException(username);
        }

        // Return the first user
        return users.get(0);
    }
    
}
