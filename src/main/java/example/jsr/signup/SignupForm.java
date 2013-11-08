package example.jsr.signup;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import example.jsr.account.Account;
import example.jsr.annotations.Anagram;
import example.jsr.annotations.NotLameEmail;
import example.jsr.annotations.PasswordAndEmailDoNotMatch;
import example.jsr.annotations.ValidateDependencies;
import example.jsr.validation.groups.AdminAccount;
import example.jsr.validation.groups.SuperstarAccount;

@ValidateDependencies
@PasswordAndEmailDoNotMatch(message = SignupForm.CANNOT_MATCH_MESSAGE, path = "email")
public class SignupForm {

	private static final String PASSWORD_MIN_LENGTH = "{password.min_length}";
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String LAME_EMAIL = "{email.lame}";
	protected static final String CANNOT_MATCH_MESSAGE = "{cannot_match}";
	private static final String MUST_CONTAIN_SPECIAL = "{password.contain_special}";
	private static final String MUST_BE_ANAGRAM = "{email.anagram}";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@NotLameEmail(message = LAME_EMAIL)
	@Anagram(message = MUST_BE_ANAGRAM, groups = SuperstarAccount.class)
	private String email;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Pattern(regexp = ".*[^0-9a-zA-Z].*", message = MUST_CONTAIN_SPECIAL, groups = AdminAccount.class)
	@Size.List({
		@Size(min = 8, message = PASSWORD_MIN_LENGTH), 
		@Size(min = 10, message = PASSWORD_MIN_LENGTH, groups = AdminAccount.class)
		})
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account createAccount() {
		return new Account(getEmail(), getPassword(), "ROLE_USER");
	}
}
