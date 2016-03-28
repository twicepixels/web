package twice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import twicebase.TwiceController;

@Controller
public class HomeController extends TwiceController {

    @RequestMapping("/")
    public String index(Model model) {
        String msg = getMessage("view.index.hello");
        model.addAttribute("message", msg);
        return "index";
    }
}