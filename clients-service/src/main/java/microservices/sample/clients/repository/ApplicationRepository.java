package microservices.sample.clients.repository;

import java.util.Optional;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import microservices.sample.clients.model.Application;
import microservices.sample.clients.model.QApplication;

/**
 * Repository for {@link Application}
 * 
 * @author Matías Hermosilla
 * @since 04-09-2021
 */
public interface ApplicationRepository extends MongoRepository<Application, String>, QuerydslPredicateExecutor<Application>, QuerydslBinderCustomizer<QApplication> {

    public Optional<Application> findByClientId(String clientId);
    
    @Override
    public default void customize(QuerydslBindings bindings, QApplication qModel) {
        bindings.bind(Long.class).first((NumberPath<Long> path, Long value) -> path.eq(value));
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(qModel.clientSecret);
    }

}
