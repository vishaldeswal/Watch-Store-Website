import com.nagarro.watchstore.dto.UserProfileDto;
import com.nagarro.watchstore.dto.UserRoleDto;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.entitytransformer.UserProfileTransformer;
import com.nagarro.watchstore.entitytransformer.UserRoleTransformer;
import com.nagarro.watchstore.enums.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test cases for UserProfileTransformer class.
 * Author: Vishal Deswal
 */
class UserProfileTransformerTest {

    @Mock
    private UserRoleTransformer roleTransformer;

    @InjectMocks
    private UserProfileTransformer userProfileTransformer;
    
    private User user;
    private UserProfileDto result;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case to transform User entity to UserProfileDto.
     * It should transform the User object to a UserProfileDto object successfully.
     */
    @Test
    void testApply_ShouldTransformUserToUserProfileDto() {
       given_User();
       when_UserProfileTransformerCall();
       then_VerifyProfileTransformSuccessfully();
    }
    
    /**
     * Sets up a User entity with valid data for testing.
     */
    private void given_User() {
    	this.user = new User();
        user.setEmailId("test@example.com");
        user.setName("John Doe");
        user.setPhoneNumber("1234567890");
        user.setRole(UserRole.ADMIN);
        when(roleTransformer.apply(any())).thenReturn(UserRoleDto.ADMIN);
    }
    
    /**
     * Calls the UserProfileTransformer's apply method to transform the User object to a UserProfileDto object.
     */
    private void when_UserProfileTransformerCall() {
    	this.result = userProfileTransformer.apply(user);
    }
    
    /**
     * Verifies that the transformation from User to UserProfileDto is successful.
     * It compares the attributes of the User and UserProfileDto objects.
     */
    private void then_VerifyProfileTransformSuccessfully() {
    	 assertNotNull(this.result);
         assertEquals(this.user.getEmailId(), result.getEmailId());
         assertEquals(this.user.getName(), result.getName());
         assertEquals(this.user.getPhoneNumber(), result.getPhoneNumber());
         assertEquals(this.user.getRole().toString(), result.getRole().toString());
         verify(roleTransformer, times(1)).apply(any());
         verifyNoMoreInteractions(roleTransformer);
    }
}
