package microservices.sample.authorities.repository;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import microservices.sample.authorities.model.Authority;
import microservices.sample.authorities.model.QAuthority;

/**
 * Repository for {@link Authority}.
 * 
 * @author Matías Hermosilla
 * @since 12-09-2021
 */
public interface AuthorityRepository extends MongoRepository<Authority, String>, QuerydslPredicateExecutor<Authority>, QuerydslBinderCustomizer<QAuthority> {
    
    @Override
    public default void customize(QuerydslBindings bindings, QAuthority qModel) {
        bindings.bind(Long.class).first((NumberPath<Long> path, Long value) -> path.eq(value));
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
