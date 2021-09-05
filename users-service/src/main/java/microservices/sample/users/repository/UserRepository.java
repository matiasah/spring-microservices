package microservices.sample.users.repository;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import microservices.sample.users.model.User;
import microservices.sample.users.model.QUser;

public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    @Override
    public default void customize(QuerydslBindings bindings, QUser qModel) {
        bindings.bind(Long.class).first((NumberPath<Long> path, Long value) -> path.eq(value));
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(qModel.password);
    }

}
