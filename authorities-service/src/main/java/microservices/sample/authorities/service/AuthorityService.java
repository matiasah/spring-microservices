package microservices.sample.authorities.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import microservices.sample.authorities.model.Authority;
import microservices.sample.authorities.repository.AuthorityRepository;

/**
 * Authority service.
 * 
 * @author Matías Hermosilla
 * @since 12-09-2021
 */
@Service
public class AuthorityService {

    /**
     * Authority repository.
     */
    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * Saves an authority.
     * 
     * @param authority Authority to save.
     */
    public void save(Authority authority) {
        // Save object to database
        this.authorityRepository.save(authority);
    }

    /**
     * Deletes an authority.
     * 
     * @param authority Authority to delete.
     */
    public void delete(Authority authority) {
        // Delete object from database
        this.authorityRepository.delete(authority);
    }
    
}
