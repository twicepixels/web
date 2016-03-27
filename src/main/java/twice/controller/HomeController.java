package twice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    MessageSource msgSource;

    @RequestMapping("/")
    public String index(Model model) {
        String msg = msgSource.getMessage("view.index.hello",
                null, "Hello world!", Locale.US);
        model.addAttribute("message", msg);
        return "index";
    }
}