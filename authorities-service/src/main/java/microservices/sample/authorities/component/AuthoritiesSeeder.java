package microservices.sample.authorities.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import microservices.sample.authorities.model.Authority;
import microservices.sample.authorities.repository.AuthorityRepository;

/**
 * Authorities Seeder
 * 
 * @author Matías Hermosilla
 * @since 15-10-2021
 */
@Component
public class AuthoritiesSeeder {

    /**
     * Authority Repository
     */
    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * Authorities Seeder
     * 
     * @param contextRefreshedEvent The context refreshed event
     */
    @EventListener
    public void seedAuthorities(ContextRefreshedEvent contextRefreshedEvent) {
        
        // If the authorities collection is empty
        if (authorityRepository.count() == 0) {
            
            // Create the admin authority object
            Authority adminAuthority = new Authority();

            // Set the authority name
            adminAuthority.setName("ROLE_ADMIN");

            // Save the authority
            authorityRepository.save(adminAuthority);
        }

    }
    
}
