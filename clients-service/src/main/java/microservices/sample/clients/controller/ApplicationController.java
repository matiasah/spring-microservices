package microservices.sample.clients.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import microservices.sample.clients.model.Application;
import microservices.sample.clients.repository.ApplicationRepository;
import microservices.sample.clients.service.ApplicationService;

/**
 * Rest controller for applications.
 * 
 * @author Matías Hermosilla
 */
@RestController
public class ApplicationController {
    
    /**
     * Application repository.
     */
    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * Application service.
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * Password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Jackson object mapper.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Spring validator.
     */
    @Autowired
    private SpringValidatorAdapter validator;

    /**
     * Get all applications.
     * 
     * @param predicate Predicate to filter applications.
     * @return List of applications.
     */
    @GetMapping
    public Iterable<Application> index(@QuerydslPredicate(root = Application.class) Predicate predicate) {

        // If predicate is not null
        if (predicate != null) {

            // Return objects filtered by predicate
            return this.applicationRepository.findAll(predicate);
        }

        // Return all objects
        return this.applicationRepository.findAll();
    }

    /**
     * Get page of applications.
     * 
     * @param predicate Predicate to filter applications.
     * @param pageable Pageable object to paginate applications.
     * @return Page of applications.
     */
    @GetMapping("page")
    public Page<Application> page(@QuerydslPredicate(root = Application.class) Predicate predicate, Pageable pageable) {

        // If predicate is not null
        if (predicate != null) {

            // Return page of objects filtered by predicate
            return this.applicationRepository.findAll(predicate, pageable);
        }

        // Return page of all objects
        return this.applicationRepository.findAll(pageable);
    }

    /**
     * Finds an application by id.
     * 
     * @param id Application id.
     * @return Application.
     */
    @GetMapping("{id}")
    public ResponseEntity<Application> get(@PathVariable("id") String id) {

        // Search object by id
        Optional<Application> optApplication = this.applicationRepository.findById(id);

        // If the object is not found
        if (optApplication.isEmpty()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // Get the object
        Application application = optApplication.get();

        // Return the object
        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    /**
     * Saves a new application.
     * 
     * @param application Application to save.
     * @return Application.
     * @throws BindException If the application is not valid.
     */
    @PostMapping
    public ResponseEntity<Application> save(@RequestBody Application application) throws BindException {

        // Remove id
        application.setId(null);

        // Search object
        Optional<Application> optApplication = this.applicationRepository.findByClientId(application.getClientId());

        // If the object is not found
        if (optApplication.isPresent()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Application already exists");
        }

        // Cipher password
        application.setClientSecret(this.passwordEncoder.encode(application.getClientSecret()));

        // Create validation object
        BindingResult result = new BeanPropertyBindingResult(application, "application");

        // Validate object
        this.validator.validate(application, result);

        // If there are errors
        if (result.hasErrors()) {
            throw new BindException(result);
        }

        // Save object
        this.applicationService.save(application);

        // Return object
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    /**
     * Patches an existing application.
     * 
     * @param application Application to patch.
     * @param request Request object containing patch data.
     * @return Application.
     * @throws IOException If the patch data is not valid.
     * @throws BindException If the patched application is not valid.
     */
    @PatchMapping("{id}")
    public ResponseEntity<Application> patch(@PathVariable("id") Application application, HttpServletRequest request) throws IOException, BindException {

        // Get old password
        String oldPassword = application.getClientSecret();

        // Patch old object
        this.objectMapper.readerForUpdating(application).readValue(request.getReader());

        // If the password was changed
        if (!application.getClientSecret().equals(oldPassword)) {

            // Cipher password
            application.setClientSecret(this.passwordEncoder.encode(oldPassword));
        }

        // Create validation object
        BindingResult result = new BeanPropertyBindingResult(application, "application");

        // Validate object
        this.validator.validate(application, result);

        // If there are errors
        if (result.hasErrors()) {
            throw new BindException(result);
        }

        // Update object
        this.applicationService.save(application);

        // Return object
        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    /**
     * Deletes an application.
     * 
     * @param id Application id.
     * @return Nothing.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        // Search object
        Optional<Application> optApplication = this.applicationRepository.findById(id);

        // If the object is not found
        if (optApplication.isEmpty()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // Get object
        Application application = optApplication.get();

        // Delete object
        this.applicationService.delete(application);

        // Return response
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
