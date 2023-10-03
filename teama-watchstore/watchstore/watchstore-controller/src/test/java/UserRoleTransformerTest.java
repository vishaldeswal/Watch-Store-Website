import com.nagarro.watchstore.dto.UserRoleDto;
import com.nagarro.watchstore.entitytransformer.UserRoleTransformer;
import com.nagarro.watchstore.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for UserRoleTransformer class.
 * Author: Vishal Deswal
 */
class UserRoleTransformerTest {
    @InjectMocks
    private UserRoleTransformer userRoleTransformer;
    private UserRole userRole;
    private UserRoleDto result;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case to transform a valid UserRole to UserRoleDto.
     * It should transform the UserRole object to a UserRoleDto object successfully.
     */
    @Test
    void testApply_ValidUserRole_ShouldTransformUserRoleToUserRoleDto() {
       given_UserRoleAdmin();
       when_UserRoleTransformerCall();
       then_VerifyUserRoleTransformed();
    }
    
    /**
     * Test case to handle a null UserRole.
     * It should throw an IllegalArgumentException when a null UserRole is passed to the UserRoleTransformer.
     */
    @Test
    void testApply_NullUserRole_ShouldThrowIllegalArgumentException() {
        given_UserRoleNull();
        then_VerifyNullPointerExceptionThrown();
    }
    
    /**
     * Sets up a valid UserRole as ADMIN for testing.
     */
    private void given_UserRoleAdmin() {
    	this.userRole = UserRole.ADMIN;
    }
    
    /**
     * Sets up a null UserRole for testing.
     */
    private void given_UserRoleNull() {
    	this.userRole = null;
    }
    
    /**
     * Calls the UserRoleTransformer's apply method to transform the UserRole object to a UserRoleDto object.
     */
    private void when_UserRoleTransformerCall() {
        this.result = userRoleTransformer.apply(userRole);
    }
    
    /**
     * Verifies that the transformation from UserRole to UserRoleDto is successful.
     * It compares the UserRoleDto object to the expected UserRoleDto.ADMIN.
     */
    private void then_VerifyUserRoleTransformed() {
        assertNotNull(result);
        assertEquals(UserRoleDto.ADMIN, result);
    }
    
    /**
     * Verifies that an IllegalArgumentException is thrown when a null UserRole is passed to the UserRoleTransformer.
     */
    private void then_VerifyNullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> userRoleTransformer.apply(userRole));
    }
}
