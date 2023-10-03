import com.nagarro.watchstore.controller.UserController;
import com.nagarro.watchstore.controller.impl.UserControllerImpl;
import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.dto.UserProfileDto;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
import com.nagarro.watchstore.model.UserInfo;
import com.nagarro.watchstore.response.ApiResponse;
import com.nagarro.watchstore.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test cases for UserControllerImpl class.
 * Author: Vishal Deswal
 */
class UserControllerImplTest {

    @Mock private UserService userService;
    @Mock private UserDetailExtractor userDetailExtractor;
    @Mock private Function<User, UserProfileDto> userProfileEntityTransformer;
    @Mock private Predicate<UserDto> registerUserDtoValidator;
    @Mock private BiPredicate<String, UserInfo> emailValidator;
    @Mock private Function<UserDto, User> registerUserDtoTransformer;
    
    private User user;
    private UserInfo userInfo;
    private UserDto userDto;
    private ApiResponse expectedResponse;
    private ApiResponse actualResponse;
    private UserProfileDto actualResult;
    private ResponseEntity<ApiResponse> registerResponseEntity;
    private ResponseEntity<UserProfileDto> profileResponseEntity;
    private UserProfileDto userProfileDto;

   
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserControllerImpl(userService,
        		userProfileEntityTransformer,
        		registerUserDtoValidator,
        		emailValidator,
        		registerUserDtoTransformer,
        		userDetailExtractor);
    }
    
    @AfterEach
    void cleanUp() {
    	this.user=null;
    	this.userDto=null;
    	this.userInfo=null;
    	this.actualResponse=null;
    	this.expectedResponse=null;
    	this.registerResponseEntity=null;
    	this.actualResult=null;
    	this.profileResponseEntity=null;
    	this.userProfileDto=null;
    }
    
    /**
     * Test case for registering a new user. It should return a created response with a success message.
     */
    @Test
    void testRegister_ShouldReturnCreatedResponse() {

    	given_User();
    	given_UserValidCheckAndMappingSuccessfull();
    	when_UserRegister();
    	thenVerifyRegister();
    }

    /**
     * Test case for retrieving the profile of a user by their email ID. It should return the user profile DTO.
     */
    @Test
    void testGetUserById_ShouldReturnUserProfileDto() {
    	given_User();
    	given_ValidEmailIdAndUserPresent();
    	when_GetProfile();
    	then_VerifyGetProfile();

       
    }
    
    /**
     * Sets up the user, user DTO, and user info for testing.
     */
    private void given_User() {
    	this.userDto = new UserDto();
    	this.user= new User();
    	this.userInfo= new UserInfo();
    	userInfo.setEmailId("test@example.com");
    	user.setEmailId("test@example.com");
    }
    
    /**
     * Sets up the necessary mocks and behavior for successful user registration validation and mapping.
     */
    private void given_UserValidCheckAndMappingSuccessfull() {
    	when(registerUserDtoValidator.test(this.userDto)).thenReturn(true);
        when(registerUserDtoTransformer.apply(this.userDto)).thenReturn(this.user);
        when(userService.addUser(this.user)).thenReturn(this.user.getEmailId());
        this.expectedResponse=new ApiResponse("User Registered Successfully: " + user.getEmailId());
    }
    
    /**
     * Sets up the necessary mocks and behavior for a valid email ID and existing user for profile retrieval.
     */
    private void given_ValidEmailIdAndUserPresent() {
    	when(userDetailExtractor.getUserInfo()).thenReturn(userInfo);
        when(emailValidator.test(this.user.getEmailId(), userInfo)).thenReturn(true);
        when(userService.findUserById(this.user.getEmailId())).thenReturn(user);
        when(userProfileEntityTransformer.apply(user)).thenReturn(userProfileDto);
    }
    
    /**
     * Performs the user registration operation.
     */
    private void when_UserRegister() {
        this.registerResponseEntity = userController.register(this.userDto);
        this.actualResponse = this.registerResponseEntity.getBody();
    }
    
    /**
     * Retrieves the user profile by the email ID.
     */
    private void when_GetProfile() {
    	this.profileResponseEntity = userController.getUserById(this.user.getEmailId());
    	this.actualResult = profileResponseEntity.getBody();
    }
    
    /**
     * Verifies the user registration operation, including the response status, message, and interactions with the mocks.
     */
    private void thenVerifyRegister() {
    	assertNotNull(this.registerResponseEntity);
        assertEquals(HttpStatus.CREATED, this.registerResponseEntity.getStatusCode());
        assertEquals(this.expectedResponse.getMessage(), this.actualResponse.getMessage());

        verify(registerUserDtoValidator, times(1)).test(this.userDto);
        verify(registerUserDtoTransformer, times(1)).apply(this.userDto);
        verify(userService, times(1)).addUser(this.user);
        verifyNoMoreInteractions(registerUserDtoValidator, registerUserDtoTransformer, userService);
    }
    
    /**
     * Verifies the retrieval of the user profile, including the response status, user profile DTO, and interactions with the mocks.
     */
    private void then_VerifyGetProfile() {
    	 assertNotNull(this.profileResponseEntity);
         assertEquals(HttpStatus.OK, profileResponseEntity.getStatusCode());
         assertEquals(this.userProfileDto, this.actualResult);

         verify(userDetailExtractor, times(1)).getUserInfo();
         verify(emailValidator, times(1)).test(this.user.getEmailId(), userInfo);
         verify(userService, times(1)).findUserById(this.user.getEmailId());
         verify(userProfileEntityTransformer, times(1)).apply(user);
         verifyNoMoreInteractions(userDetailExtractor, emailValidator, userService, userProfileEntityTransformer);
    }
}
