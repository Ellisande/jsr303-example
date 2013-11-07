package example.jsr.signup;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import example.jsr.support.MessageHelper;

@RequestMapping(value = "enhancedSignup")
@Controller
public class EnhancedSignupController {

//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView enhancedSignup() {
//		return new ModelAndView("enhancedSignup","enhancedSignupForm", new EnhancedSignupForm());
//	}
	@RequestMapping(method = RequestMethod.GET)
	public EnhancedSignupForm enhancedSignup() {
		return new EnhancedSignupForm();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String validateAndContinue(@Valid @ModelAttribute EnhancedSignupForm enhancedSignupForm, Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			return null;
		}

		MessageHelper.addSuccessAttribute(ra, "Congratulations! You have successfully signed up.");

		return "redirect:/";
	}
}
