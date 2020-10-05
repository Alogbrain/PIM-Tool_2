package vn.elca.training.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.elca.training.service.ProjectService;

/**
 * @author vlp
 *
 */
@Controller
public class ApplicationController {

    @Autowired
    private ProjectService projectService;
    
    @Value("${total.number.of.projects}")
    private String message;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView main() {
        Map<String, Object> model = new HashMap<String, Object>() {
            private static final long serialVersionUID = -6883088231537577238L;
            {
                put("title", "Project Management Tool");
//                put("message", String.format(message, projectService.findAll().size()));
            }
        };
        return new ModelAndView("search", model);
    }
}
