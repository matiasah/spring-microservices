package microservices.sample.authorities.controller;

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
import microservices.sample.authorities.model.Authority;
import microservices.sample.authorities.repository.AuthorityRepository;
import microservices.sample.authorities.service.AuthorityService;

/**
 * Rest controller for authorities.
 */
@RestController
public class AuthorityController {
    
    /**
     * Authority repository.
     */
    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * Authority service.
     */
    @Autowired
    private AuthorityService authorityService;

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
     * Get all authorities.
     * 
     * @param predicate Predicate to filter authorities.
     * @return List of authorities.
     */
    @GetMapping
    public Iterable<Authority> index(@QuerydslPredicate(root = Authority.class) Predicate predicate) {

        // If predicate is not null
        if (predicate != null) {

            // Return objects filtered by predicate
            return this.authorityRepository.findAll(predicate);
        }

        // Return all objects
        return this.authorityRepository.findAll();
    }

    /**
     * Get page of authorities.
     * 
     * @param predicate Predicate to filter authorities.
     * @param pageable Pageable object to paginate authorities.
     * @return Page of authorities.
     */
    @GetMapping("page")
    public Page<Authority> page(@QuerydslPredicate(root = Authority.class) Predicate predicate, Pageable pageable) {

        // If predicate is not null
        if (predicate != null) {

            // Return page of objects filtered by predicate
            return this.authorityRepository.findAll(predicate, pageable);
        }

        // Return page of all objects
        return this.authorityRepository.findAll(pageable);
    }

    /**
     * Finds an authority by id.
     * 
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Authority> get(@PathVariable("id") String id) {

        // Search object by id
        Optional<Authority> optAuthority = this.authorityRepository.findById(id);

        // If the object is not found
        if (optAuthority.isEmpty()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // Get the object
        Authority authority = optAuthority.get();

        // Return the object
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }

    /**
     * Saves a new authority.
     * 
     * @param authority Authority to save.
     * @return Saved authority.
     * @throws BindException If the object is not valid.
     */
    @PostMapping
    public ResponseEntity<Authority> save(@RequestBody Authority authority) throws BindException {

        // Remove id
        authority.setId(null);

        // Create validation object
        BindingResult result = new BeanPropertyBindingResult(authority, "authority");

        // Validate object
        this.validator.validate(authority, result);

        // If there are errors
        if (result.hasErrors()) {
            throw new BindException(result);
        }

        // Save object
        this.authorityService.save(authority);

        // Return object
        return new ResponseEntity<>(authority, HttpStatus.CREATED);
    }

    /**
     * Patches an existing authority.
     * 
     * @param authority Authority to patch.
     * @param request Request object containing patch data.
     * @return Patched authority.
     * @throws IOException If the patch data is not valid.
     * @throws BindException If the patched authority is not valid.
     */
    @PatchMapping("{id}")
    public ResponseEntity<Authority> patch(@PathVariable("id") Authority authority, HttpServletRequest request) throws IOException, BindException {

        // Patch old object
        this.objectMapper.readerForUpdating(authority).readValue(request.getReader());

        // Create validation object
        BindingResult result = new BeanPropertyBindingResult(authority, "authority");

        // Validate object
        this.validator.validate(authority, result);

        // If there are errors
        if (result.hasErrors()) {
            throw new BindException(result);
        }

        // Update object
        this.authorityService.save(authority);

        // Return object
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }

    /**
     * Deletes an authority.
     * 
     * @param id Authority id.
     * @return Nothing.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        // Search object
        Optional<Authority> optAuthority = this.authorityRepository.findById(id);

        // If the object is not found
        if (optAuthority.isEmpty()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // Get object
        Authority authority = optAuthority.get();

        // Delete object
        this.authorityService.delete(authority);

        // Return response
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
