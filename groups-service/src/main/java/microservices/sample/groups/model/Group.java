package microservices.sample.groups.model;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Document
public class Group {

    /**
     * Application id.
     */
    @Id
    private String id;

    /**
     * Group name.
     */
    private String name;

    /**
     * Date of creation.
     */
    @CreatedDate
    private ZonedDateTime createdDate;

    /**
     * Date of last modification.
     */
    @LastModifiedDate
    private ZonedDateTime lastModifiedDate;
    
}
