package microservices.sample.users.controller;

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
import microservices.sample.users.model.User;
import microservices.sample.users.repository.UserRepository;
import microservices.sample.users.service.UserService;

/**
 * User controller.
 * 
 * @author Matías Hermosilla
 * @since 05-09-2021
 */
@RestController
public class UserController {

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * User service.
     */
    @Autowired
    private UserService userService;

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
     * Get all users.
     * 
     * @param predicate Predicate to filter users.
     * @return List of users.
     */
    @GetMapping
    public Iterable<User> index(@QuerydslPredicate(root = User.class) Predicate predicate) {

        // If predicate is not null
        if (predicate != null) {

            // Return objects filtered by predicate
            return this.userRepository.findAll(predicate);
        }

        // Return all objects
        return this.userRepository.findAll();
    }

    /**
     * Get a page of users.
     * 
     * @param predicate Predicate to filter users.
     * @param pageable Pageable object to paginate users.
     * @return Page of users.
     */
    @GetMapping("page")
    public Page<User> page(@QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable) {

        // If predicate is not null
        if (predicate != null) {

            // Return page of objects filtered by predicate
            return this.userRepository.findAll(predicate, pageable);
        }

        // Return page of all objects
        return this.userRepository.findAll(pageable);
    }

    /**
     * Finds a user by id.
     * 
     * @param id User id.
     * @return User.
     */
    @GetMapping("{id}")
    public ResponseEntity<User> get(@PathVariable("id") String id) {

        // Search object by id
        Optional<User> optUser = this.userRepository.findById(id);

        // If the object is not found
        if (optUser.isEmpty()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // Get the object
        User user = optUser.get();

        // Return the object
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Saves a new user.
     * 
     * @param user User to save.
     * @return User.
     * @throws BindException If the user is not valid.
     */
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) throws BindException {

        // Remove id
        user.setId(null);

        // Search object
        Optional<User> optUser = this.userRepository.findByUsername(user.getUsername());

        // If the object is not found
        if (optUser.isPresent()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        // Cipher password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // Enable user
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        // Create validation object
        BindingResult result = new BeanPropertyBindingResult(user, "user");

        // Validate object
        this.validator.validate(user, result);

        // If there are errors
        if (result.hasErrors()) {
            throw new BindException(result);
        }

        // Save object
        this.userService.save(user);

        // Return object
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Patches an existing user.
     * 
     * @param user User to patch.
     * @param request Request object containing patch data.
     * @return User.
     * @throws IOException If the patch data is not valid.
     * @throws BindException If the patched user is not valid.
     */
    @PatchMapping("{id}")
    public ResponseEntity<User> patch(@PathVariable("id") User user, HttpServletRequest request) throws IOException, BindException {

        // Get old password
        String oldPassword = user.getPassword();

        // Patch old object
        this.objectMapper.readerForUpdating(user).readValue(request.getReader());

        // If the password was changed
        if (!user.getPassword().equals(oldPassword)) {

            // Cipher password
            user.setPassword(this.passwordEncoder.encode(oldPassword));
        }

        // Create validation object
        BindingResult result = new BeanPropertyBindingResult(user, "user");

        // Validate object
        this.validator.validate(user, result);

        // If there are errors
        if (result.hasErrors()) {
            throw new BindException(result);
        }

        // Update object
        this.userService.save(user);

        // Return object
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Deletes a user by id.
     * 
     * @param id User id.
     * @return Nothing.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        // Search object
        Optional<User> optUser = this.userRepository.findById(id);

        // If the object is not found
        if (optUser.isEmpty()) {

            // Throw exception
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // Get object
        User user = optUser.get();

        // Delete object
        this.userService.delete(user);

        // Return response
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
