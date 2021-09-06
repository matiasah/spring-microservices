package microservices.sample.clients.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import microservices.sample.clients.model.Application;
import microservices.sample.clients.repository.ApplicationRepository;

/**
 * Application service.
 * 
 * @author Matías Hermosilla
 * @since 05-09-2021
 */
@Service
public class ApplicationService {

    /**
     * Application repository.
     */
    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * Saves an application.
     * 
     * @param application Application to save.
     */
    public void save(Application application) {
        // Save object to database
        this.applicationRepository.save(application);
    }

    /**
     * Deletes an application.
     * 
     * @param application Application to delete.
     */
    public void delete(Application application) {
        // Delete object from database
        this.applicationRepository.delete(application);
    }
    
}
