package microservices.sample.groups.repository;

import com.querydsl.core.group.Group;
import com.querydsl.core.group.QGroup;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * Repository for {@link Group}s.
 * 
 * @author Matías Hermosilla
 * @since 04-09-2021
 */
public interface GroupRepository extends MongoRepository<Group, String>, QuerydslPredicateExecutor<Group>, QuerydslBinderCustomizer<QGroup> {

    @Override
    public default void customize(QuerydslBindings bindings, QGroup qModel) {
        bindings.bind(Long.class).first((NumberPath<Long> path, Long value) -> path.eq(value));
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
    
}
