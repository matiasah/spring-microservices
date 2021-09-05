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

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpringValidatorAdapter validator;

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

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) throws BindException {

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
