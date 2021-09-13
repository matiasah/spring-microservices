package microservices.sample.authorities.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Authority model.
 * 
 * @author Matías Hermosilla
 * @since 12-09-2021
 */
@Data
@EqualsAndHashCode(of = "id")
@Document
public class Authority implements GrantedAuthority {
    
    /**
     * Authority id.
     */
    @Id
    private String id;

    /**
     * Authority name.
     */
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
    
}
