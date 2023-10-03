package com.nagarro.watchstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nagarro.watchstore.dao.UserRepository;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.exception.UserNotFoundException;
import com.nagarro.watchstore.service.UserService;
import com.nagarro.watchstore.exception.UserAlreadyExistException;

/**
 * Test cases for UserServiceImpl class.
 * Author: Vishal Deswal
 */
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private User user;
    private String searchedEmailId;
    private String savedUserEmail;
    private User searchResultUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
        given_User(); // Act as a new user for addUser and also as an existing User for searchUser()
    }

    /**
     * Test case for adding a new user.
     */
    @Test
    public void testAddUser_NewUser() {
        given_UserNotExistRegisterNewUser();
        when_AddUserCalled();
        then_VerifyUserAdded();
    }

    /**
     * Test case for adding an existing user.
     */
    @Test
    public void testAddUser_ExistingUser() {
        given_UserExistAlready(user.getEmailId());
        then_VerifyUserAlreadyExistExceptionThrow();
    }

    /**
     * Test case for finding an existing user by their ID.
     */
    @Test
    public void testFindUserById_ExistingUser() {
        given_SearchedEmail();
        given_UserExistAlready(searchedEmailId);
        when_UserSearchInitialized();
        then_VerifyUserFoundSuccessfully();
    }

    /**
     * Test case for finding a non-existing user by their ID.
     */
    @Test
    public void testFindUserById_NonExistingUser() {
        given_SearchedEmail();
        given_UserNotExistAlready(searchedEmailId);
        then_VerifyUserNotFoundExceptionThrow();
    }

    /**
     * Sets up the email ID for the searched user.
     */
    private void given_SearchedEmail() {
        this.searchedEmailId = "test@example.com";
    }

    /**
     * Creates a new user object for testing.
     */
    private void given_User() {
        this.user = new User();
        user.setEmailId("test@example.com");
    }
    
    /**
     * Creates an Optional instance of the given user for mocking repository behavior.
     */
    private Optional<User> optionalOfGivenUser(){
        return Optional.of(this.user);
    }

    /**
     * Sets up the behavior of the repository to return an existing user with the given email ID.
     */
    private void given_UserExistAlready(String emailId) {
        when(userRepository.findById(emailId)).thenReturn(optionalOfGivenUser());
    }

    /**
     * Sets up the behavior of the repository to return an empty optional for a non-existing user with the given email ID.
     */
    private void given_UserNotExistAlready(String emailId) {
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
    }

    /**
     * Sets up the behavior of the repository when saving a new user.
     */
    private void given_UserNotExistRegisterNewUser() {
        given_UserNotExistAlready(user.getEmailId());
        when(userRepository.save(user)).thenReturn(user);
    }

    /**
     * Calls the addUser method of the userService.
     */
    private void when_AddUserCalled() {
        this.savedUserEmail = userService.addUser(user);
    }

    /**
     * Initializes the search for a user by their email ID.
     */
    private void when_UserSearchInitialized() {
        this.searchResultUser = userService.findUserById(searchedEmailId);
    }

    /**
     * Verifies that the user is added successfully.
     */
    private void then_VerifyUserAdded() {
        assertEquals(user.getEmailId(), savedUserEmail);
        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Verifies that a UserAlreadyExistException is thrown when trying to add an existing user.
     */
    private void then_VerifyUserAlreadyExistExceptionThrow() {
        assertThrows(UserAlreadyExistException.class, () -> userService.addUser(user));
        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, never()).save(user);
    }

    /**
     * Verifies that a UserNotFoundException is thrown when searching for a non-existing user.
     */
    private void then_VerifyUserNotFoundExceptionThrow() {
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(searchedEmailId));
        verify(userRepository, times(1)).findById(searchedEmailId);
    }

    /**
     * Verifies that a user is found successfully.
     */
    private void then_VerifyUserFoundSuccessfully() {
        assertEquals(user, searchResultUser);
        verify(userRepository, times(1)).findById(searchedEmailId);
    }
}
