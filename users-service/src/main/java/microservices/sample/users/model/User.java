package microservices.sample.users.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User entity.
 * 
 * @author Matías Hermosilla
 * @since 05-09-2021
 */
@Data
@EqualsAndHashCode(of = "id")
@Document
public class User implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    private String id;

    /**
     * List of authorities granted to the user.
     */
    private List<GrantedAuthority> authorities;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's username.
     */
    private String username;

    /**
     * Indicates whether the user's account has expired.
     */
    private boolean accountNonExpired;

    /**
     * Indicates whether the user's account is locked.
     */
    private boolean accountNonLocked;

    /**
     * Indicates whether the user's credentials (password) has expired.
     */
    private boolean credentialsNonExpired;

    /**
     * Indicates whether the user is enabled or disabled.
     */
    private boolean enabled;
    
}
