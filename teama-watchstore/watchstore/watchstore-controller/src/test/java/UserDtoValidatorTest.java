import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.validator.UserDtoValidator;
/**
 * Test cases for UserDtoValidator class.
 * Author: Vishal Deswal
 */
public class UserDtoValidatorTest {

    private UserDtoValidator userDtoValidator;
    private UserDto userDto;
    private boolean result;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDtoValidator = new UserDtoValidator();
        given_ValidUserDto();
    }
    
    /**
     * Test case for a valid UserDto object.
     * It should return true.
     */
    @Test
    void test_ValidUserDto_ShouldReturnTrue() {
    	when_UserDtoValidate();
    	then_VerifyUserDtoValidateSuccessfully();
    }

    /**
     * Test case for an invalid email in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_InvalidEmail_ShouldThrowBadRequestException() {
        given_InvalidEmailInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for a null email in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_NullEmail_ShouldThrowBadRequestException() {
        given_NullEmailInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for an empty email in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_EmptyEmail_ShouldThrowBadRequestException() {
        given_EmptyEmailInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for a null name in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_NullName_ShouldThrowBadRequestException() {
        given_NullNameInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for an empty name in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_EmptyName_ShouldThrowBadRequestException() {
        given_EmptyNameInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for an invalid name in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_InvalidName_ShouldThrowBadRequestException() {
    	given_InvalidNameInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for a null password in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_NullPassword_ShouldThrowBadRequestException() {
    	given_NullPasswordInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for an empty password in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_EmptyPassword_ShouldThrowBadRequestException() {
    	given_EmptyPasswordInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }

    /**
     * Test case for an invalid password in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_InvalidPassword_ShouldThrowBadRequestException() {
    	given_InvalidPasswordInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for a null phone number in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_NullPhoneNumber_ShouldThrowBadRequestException() {
    	given_NullPhoneInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Test case for an empty phone number in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_EmptyPhoneNumber_ShouldThrowBadRequestException() {
    	given_EmptyPhoneInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }

    /**
     * Test case for an invalid phone number in the UserDto object.
     * It should throw a BadRequestException.
     */
    @Test
    void test_InvalidPhoneNumber_ShouldThrowBadRequestException() {
    	given_InvalidPhoneInUserDto();
        then_VerifyBadRequestExceptionThrow();
    }
    
    /**
     * Sets up a valid UserDto object for testing.
     */
    private void given_ValidUserDto() {
        this.userDto = new UserDto();
        userDto.setEmailId("test@example.com");
        userDto.setName("John Doe");
        userDto.setPassword("Password@123");
        userDto.setPhoneNumber("1234567890");
    }
    
    /**
     * Sets an invalid email in the UserDto object for testing.
     */
    private void given_InvalidEmailInUserDto() {
        this.userDto.setEmailId("invalidemail");
    }
    
    /**
     * Sets a null email in the UserDto object for testing.
     */
    private void given_NullEmailInUserDto() {
        this.userDto.setEmailId(null);
    }
    
    /**
     * Sets an empty email in the UserDto object for testing.
     */
    private void given_EmptyEmailInUserDto() {
        this.userDto.setEmailId("");
    }
    
    /**
     * Sets an invalid name in the UserDto object for testing.
     */
    private void given_InvalidNameInUserDto() {
        this.userDto.setName("Invalid123_Name");
    }
    
    /**
     * Sets a null name in the UserDto object for testing.
     */
    private void given_NullNameInUserDto() {
        this.userDto.setName(null);
    }
    
    /**
     * Sets an empty name in the UserDto object for testing.
     */
    private void given_EmptyNameInUserDto() {
        this.userDto.setName("");
    }
    
    /**
     * Sets an invalid password in the UserDto object for testing.
     */
    private void given_InvalidPasswordInUserDto() {
        this.userDto.setPassword("invalid@Password");
    }
    
    /**
     * Sets a null password in the UserDto object for testing.
     */
    private void given_NullPasswordInUserDto() {
        this.userDto.setPassword(null);
    }
    
    /**
     * Sets an empty password in the UserDto object for testing.
     */
    private void given_EmptyPasswordInUserDto() {
        this.userDto.setPassword("");
    }
    
    /**
     * Sets an invalid phone number in the UserDto object for testing.
     */
    private void given_InvalidPhoneInUserDto() {
        userDto.setPhoneNumber("invalidphone");
    }
    
    /**
     * Sets a null phone number in the UserDto object for testing.
     */
    private void given_NullPhoneInUserDto() {
        userDto.setPhoneNumber(null);
    }
    
    /**
     * Sets an empty phone number in the UserDto object for testing.
     */
    private void given_EmptyPhoneInUserDto() {
        userDto.setPhoneNumber("");
    }
    
    /**
     * Calls the UserDtoValidator's test method to validate the UserDto object.
     */
    private void when_UserDtoValidate() {
        this.result = userDtoValidator.test(userDto);
    }
    
    /**
     * Verifies that a BadRequestException is thrown when validating the UserDto object.
     */
    private void then_VerifyBadRequestExceptionThrow() {
        assertThrows(BadRequestException.class, () -> userDtoValidator.test(this.userDto));
    }
    
    /**
     * Verifies that the UserDto object is validated successfully.
     */
    private void then_VerifyUserDtoValidateSuccessfully() {
        assertTrue(this.result);
    }
}
