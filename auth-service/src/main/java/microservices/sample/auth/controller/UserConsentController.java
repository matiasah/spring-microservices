package microservices.sample.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserConsentController {
    
    @GetMapping("/oauth2/consent")
    public String consent() {
        return "index";
    }

}
