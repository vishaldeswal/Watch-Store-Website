import com.nagarro.watchstore.exception.InvalidEmailArgumentException;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.model.UserInfo;
import com.nagarro.watchstore.validator.EmailValidator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for EmailValidator class. Author: Vishal Deswal
 */
class EmailValidatorTest {

	@Mock
	private UserInfo userInfo;

	@InjectMocks
	private EmailValidator emailValidator;

	private boolean expectedResult;
	private boolean actualResult;
	private String emailId;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		emailValidator = new EmailValidator();
	}

	@AfterEach
	void cleanUp() {
		emailValidator = null;
	}

	/**
	 * Test case for validating a valid email that matches the user's email ID.
	 */
	@Test
	void testValidEmailAndMatchingUserId_ShouldReturnTrue() {
		given_ValidEmail();
		given_EmailIdMatchSuccesfull();
		when_EmailValidate();
		then_VerifyEmailValidation();
	}

	/**
	 * Test case for validating a valid email that does not match the user's email
	 * ID. It should throw an InvalidEmailArgumentException.
	 */
	@Test
	void testValidEmailButNotMatchingUserId_ShouldThrowInvalidEmailArgumentException() {
		given_ValidEmail();
		given_EmailIdMatchFail();
		then_VerifyInvalidInvalidEmailArgumentExceptionThrow();
	}

	/**
	 * Test case for validating an invalid email. It should throw an
	 * InvalidEmailArgumentException.
	 */
	@Test
	void testInvalidEmail_ShouldThrowInvalidEmailArgumentException() {
		given_InvalidEmail();
		then_VerifyBadRequestExceptionThrow();
	}

	/**
	 * Test case for validating a null email. It should throw an
	 * InvalidEmailArgumentException.
	 */
	@Test
	void testNullEmail_ShouldThrowInvalidEmailArgumentException() {
		given_NullEmail();
		then_VerifyBadRequestExceptionThrow();
	}

	/**
	 * Test case for validating an empty email. It should throw an
	 * InvalidEmailArgumentException.
	 */
	@Test
	void testEmptyEmail_ShouldThrowInvalidEmailArgumentException() {
		given_EmptyEmail();
		then_VerifyBadRequestExceptionThrow();
	}

	/**
	 * Sets up a valid email.
	 */
	private void given_ValidEmail() {
		this.emailId = "test@example.com";
	}

	/**
	 * Sets up an invalid email.
	 */
	private void given_InvalidEmail() {
		this.emailId = "Invalid_Email@123,com";
	}

	/**
	 * Sets up a null email.
	 */
	private void given_NullEmail() {
		this.emailId = null;
	}

	/**
	 * Sets up an empty email.
	 */
	private void given_EmptyEmail() {
		this.emailId = "";
	}

	/**
     * Sets up the user's email ID to match with the provided email for validation.
     */
    private void given_EmailIdMatchSuccesfull() {
        when(userInfo.getEmailId()).thenReturn("test@example.com");
    }

	/**
     * Sets up the user's email ID to not match with the provided email for validation.
     */
    private void given_EmailIdMatchFail() {
        when(userInfo.getEmailId()).thenReturn("another@example.com");
    }

	/**
	 * Performs the email validation.
	 */
	private void when_EmailValidate() {
		this.actualResult = emailValidator.test(this.emailId, userInfo);
	}

	/**
	 * Verifies that the email is valid and matches the user's email ID.
	 */
	private void then_VerifyEmailValidation() {
		assertTrue(this.actualResult);
		verify(userInfo, times(1)).getEmailId();
		verifyNoMoreInteractions(userInfo);
	}

	/**
	 * Verifies that an InvalidEmailArgumentException is thrown when the email is
	 * valid but does not match the user's email ID.
	 */
	private void then_VerifyInvalidInvalidEmailArgumentExceptionThrow() {
		assertThrows(InvalidEmailArgumentException.class, () -> emailValidator.test(this.emailId, userInfo));
		verify(userInfo, times(1)).getEmailId();
		verifyNoMoreInteractions(userInfo);
	}

	/**
	 * Verifies that a BadRequestException is thrown when the email is invalid.
	 */
	private void then_VerifyBadRequestExceptionThrow() {
		assertThrows(BadRequestException.class, () -> emailValidator.test(this.emailId, userInfo));
		verify(userInfo, times(1)).getEmailId();
		verifyNoMoreInteractions(userInfo);
	}
}
