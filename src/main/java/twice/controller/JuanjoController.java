package twice.controller;

/**
 * Created by Juanjo on 28/03/2016.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import twicebase.TwiceController;

@Controller
@RequestMapping("/juanjo1")
public class JuanjoController extends TwiceController {
    @RequestMapping("/indexJ")
    public String indexJ(Model model) {
       // String msg = getMessage("view.juanjo.hello");
        model.addAttribute("message", "Juanjose");
        return "juanjo1";
    }
}
