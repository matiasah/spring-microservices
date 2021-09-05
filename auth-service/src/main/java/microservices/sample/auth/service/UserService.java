package microservices.sample.auth.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import microservices.sample.auth.model.User;

@FeignClient(name = "users-service", path = "/users")
public interface UserService {

    @GetMapping
    public List<User> findAllByUsername(@RequestParam("username") String username);

}
