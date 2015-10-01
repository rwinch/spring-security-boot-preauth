package sample;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(Principal principal, Map<String,Object> model) {
		model.put("username", principal.getName());
		return "index";
	}
}