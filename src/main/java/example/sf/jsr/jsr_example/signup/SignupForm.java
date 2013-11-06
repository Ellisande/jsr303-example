package example.sf.jsr.jsr_example.signup;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import example.sf.jsr.jsr_example.account.Account;
import example.sf.jsr.jsr_example.annotations.Anagram;
import example.sf.jsr.jsr_example.annotations.NotLameEmail;
import example.sf.jsr.jsr_example.annotations.PasswordAndEmailDoNotMatch;
import example.sf.jsr.jsr_example.validation.groups.AdminAccount;
import example.sf.jsr.jsr_example.validation.groups.SuperstarAccount;

@PasswordAndEmailDoNotMatch(message=SignupForm.CANNOT_MATCH_MESSAGE, path="email")
public class SignupForm {

	private static final String PASSWORD_MIN_LENGTH = "{password.min_length}";
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String LAME_EMAIL = "{email.lame}";
	public static final String CANNOT_MATCH_MESSAGE = "{cannot_match}";
	protected static final String MUST_CONTAIN_SPECIAL = "{password.contain_special}";

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
    @NotLameEmail.List({@NotLameEmail(message = LAME_EMAIL), @NotLameEmail(message=LAME_EMAIL) })
    @Anagram(message="Your email address must be an anagram (the same forwards and backwards)", groups=SuperstarAccount.class)
	private String email;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
    @Pattern(regexp=".*[^0-9a-zA-Z].*", message=MUST_CONTAIN_SPECIAL, groups=AdminAccount.class)
    @Size(min=8, message=PASSWORD_MIN_LENGTH, groups = AdminAccount.class)
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
