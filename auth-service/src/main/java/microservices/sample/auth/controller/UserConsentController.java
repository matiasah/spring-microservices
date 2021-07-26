package microservices.sample.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserConsentController {
    
    @GetMapping("/oauth2/consent")
    public ModelAndView consent() {
        return new ModelAndView("index.html");
    }

}
