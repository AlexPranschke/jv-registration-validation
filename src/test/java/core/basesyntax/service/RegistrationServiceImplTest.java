package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin("validLogin");
        validUser.setPassword("validPassword");
        validUser.setAge(25);
        User registeredUser = registrationService.register(validUser);
        assertNotNull(registeredUser);
        assertEquals(validUser.getLogin(), registeredUser.getLogin());
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_throwsCustomException() {
        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(null)
        );

        assertEquals("Incorrect data", ex.getMessage());
    }

    @Test
    void register_userWithShortPassword_throwsCustomException() {
        User userWithShortPassword = new User();
        userWithShortPassword.setLogin("user1");
        userWithShortPassword.setPassword("123");
        userWithShortPassword.setAge(25);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithShortPassword)
        );

        assertEquals("Password should be at least 6 characters", ex.getMessage());
    }

    @Test
    void register_userWithShortLogin_throwsCustomException() {
        User userWithShortLogin = new User();
        userWithShortLogin.setLogin("usr");
        userWithShortLogin.setPassword("validPassword");
        userWithShortLogin.setAge(25);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithShortLogin)
        );

        assertEquals("Login should be at least 4 characters", ex.getMessage());
    }

    @Test
    void register_userUnder18_throwsCustomException() {
        User underageUser = new User();
        underageUser.setLogin("user2");
        underageUser.setPassword("validPassword");
        underageUser.setAge(17);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(underageUser)
        );

        assertEquals("User should over least 18 years old", ex.getMessage());
    }

     @Test
    void register_userWithExistingLogin_throwsCustomException() {
        User firstUser = new User();
        firstUser.setLogin("existingUser");
        firstUser.setPassword("validPassword");
        firstUser.setAge(25);
        registrationService.register(firstUser);

        User secondUser = new User();
        secondUser.setLogin("existingUser");
        secondUser.setPassword("anotherPassword");
        secondUser.setAge(30);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(secondUser)
        );

        assertEquals("User with login existingUser already exists", ex.getMessage());
        }

}