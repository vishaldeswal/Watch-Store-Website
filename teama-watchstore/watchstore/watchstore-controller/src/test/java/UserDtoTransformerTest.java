import com.nagarro.watchstore.dto.UserDto;
import com.nagarro.watchstore.dto.UserRoleDto;
import com.nagarro.watchstore.dtotransformer.UserDtoTransformer;
import com.nagarro.watchstore.dtotransformer.UserRoleDtoTransformer;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for UserDtoTransformer class.
 * Author: Vishal Deswal
 */
class UserDtoTransformerTest {

	@Mock
	private UserRoleDtoTransformer roleDtoTransformer;
	
	private UserDto userDto;
	private User result;

	@InjectMocks
	private UserDtoTransformer userDtoTransformer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test case for transforming a UserDto object to a User entity.
	 */
	@Test
	void testApply_ShouldTransformUserDtoToUserEntity() {
		given_ValidUserDto();
		when_TransformerCall();
		then_VerifyTransformedSuccessfully();
	}

	/**
	 * Sets up a valid UserDto object for testing.
	 */
	private void given_ValidUserDto() {
		this.userDto = new UserDto();
		userDto.setEmailId("test@example.com");
		userDto.setName("John Doe");
		userDto.setPassword("password");
		userDto.setRole(UserRoleDto.ADMIN.getUserType());
		userDto.setPhoneNumber("1234567890");

		when(roleDtoTransformer.apply(UserRoleDto.ADMIN)).thenReturn(UserRole.ADMIN);
	}

	/**
	 * Calls the UserDtoTransformer's apply method to transform the UserDto object to a User entity.
	 */
	private void when_TransformerCall() {
		this.result = userDtoTransformer.apply(userDto);
	}

	/**
	 * Verifies that the UserDto object is successfully transformed to a User entity.
	 */
	private void then_VerifyTransformedSuccessfully() {

		assertNotNull(this.result);
		assertEquals(this.userDto.getEmailId(), result.getEmailId());
		assertEquals(this.userDto.getName(), result.getName());
		assertTrue(BCrypt.checkpw(this.userDto.getPassword(), result.getPassword()));
		assertEquals(this.userDto.getRole().toString(), result.getRole().toString());
		assertEquals(this.userDto.getPhoneNumber(), result.getPhoneNumber());

		verify(roleDtoTransformer, times(1)).apply(UserRoleDto.ADMIN);
		verifyNoMoreInteractions(roleDtoTransformer);
	}

}
