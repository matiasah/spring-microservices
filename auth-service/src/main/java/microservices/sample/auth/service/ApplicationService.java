package microservices.sample.auth.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import microservices.sample.auth.model.Application;

@FeignClient(name = "clients-service", path = "/clients")
public interface ApplicationService {

    @GetMapping("{id}")
    public Application findById(@PathVariable("id") String id);

    @GetMapping
    public List<Application> findAllByClientId(@RequestParam("clientId") String clientId);

    @PostMapping
    public Application save(@RequestBody Application application);
    
}
