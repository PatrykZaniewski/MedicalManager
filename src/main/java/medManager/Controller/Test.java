package medManager.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping(value = "/xd")
    public String test(){
        return new String("test");
    }
}
