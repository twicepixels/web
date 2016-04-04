package twice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import twice.model.PersonForm;
import twicebase.JsonResult;
import twicebase.TwiceController;

import javax.validation.Valid;

@Controller
public class HomeController extends TwiceController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, PersonForm personForm) {
        String msg = getMessage("view.index.hello");
        model.addAttribute("pagetitle", "My title");
        model.addAttribute("message", msg);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView checkPerson(@Valid PersonForm personForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (!result.hasErrors()) {
            mav.setViewName("_result");
            mav.addObject("personForm", personForm);
        } else if (isAjaxRequest()) {
            System.out.println("Request por XMLHttp");
            mav = JsonResult.render(getErrors(result));
        } else {
            mav.setViewName("index");
            mav.addAllObjects(result.getModel());
        }
        return mav;
    }
}