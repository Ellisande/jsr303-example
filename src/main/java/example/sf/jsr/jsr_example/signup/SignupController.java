package example.sf.jsr.jsr_example.signup;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import example.sf.jsr.jsr_example.account.Account;
import example.sf.jsr.jsr_example.account.AccountRepository;
import example.sf.jsr.jsr_example.account.UserService;
import example.sf.jsr.jsr_example.support.web.MessageHelper;
import example.sf.jsr.jsr_example.validation.groups.AdminAccount;
import example.sf.jsr.jsr_example.validation.groups.SuperstarAccount;

@Controller
@RequestMapping(value="signup")
public class SignupController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public SignupForm signup() {
		return new SignupForm();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			return null;
		}
		
		Account account = accountRepository.save(signupForm.createAccount());
		userService.signin(account);

        MessageHelper.addSuccessAttribute(ra, "Congratulations! You have successfully signed up.");
		
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, params="wrong")
	public String wrong(Model model, Errors errors, @Valid SignupForm signupForm){
		if(errors.hasErrors()){
			return null;
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, params="admin")
	public String signupAdmin(@Validated(value={Default.class, AdminAccount.class}) @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra, Model model) {
		return signup(signupForm, errors, ra, model);
	}
	@RequestMapping(method = RequestMethod.POST, params="super")
	public String signupSuper(@Validated(value={Default.class, SuperstarAccount.class}) @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra, Model model) {
		return signup(signupForm, errors, ra, model);
	}
}
