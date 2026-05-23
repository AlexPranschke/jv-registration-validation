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
        Storage.people.add(validUser);
        assertNotNull(validUser);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_throwsCustomException() {
        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(null)
        );

        assertEquals("User should not be null", ex.getMessage());
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
        userWithShortLogin.setLogin("gsdde");
        userWithShortLogin.setPassword("validPassword");
        userWithShortLogin.setAge(25);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithShortLogin)
        );

        assertEquals("Login should be at least 6 characters", ex.getMessage());
    }

    @Test
    void register_userWithOkLogin() {
        User userWithOkLogin = new User();
        userWithOkLogin.setLogin("gsddef");
        userWithOkLogin.setPassword("validPassword");
        userWithOkLogin.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(userWithOkLogin));
    }

    @Test
    void register_userWith8CharLogin_ok() {
        User userWith8CharLogin = new User();
        userWith8CharLogin.setLogin("gsddefgg");
        userWith8CharLogin.setPassword("validPassword");
        userWith8CharLogin.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(userWith8CharLogin));
    }

    @Test
    void register_userUnder18_throwsCustomException() {
        User underageUser = new User();
        underageUser.setLogin("NewUser");
        underageUser.setPassword("validPassword");
        underageUser.setAge(17);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(underageUser)
        );

        assertEquals("User should be at least 18 years old", ex.getMessage());
    }

    @Test
    void register_userWithNegativeAge_throwsCustomException() {
        User userWithNegativeAge = new User();
        userWithNegativeAge.setLogin("NewUser");
        userWithNegativeAge.setPassword("validPassword");
        userWithNegativeAge.setAge(-5);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithNegativeAge)
        );

        assertEquals("User should be at least 18 years old", ex.getMessage());
    }

    @Test
    void register_userWithOkAge() {
        User userWithOkAge = new User();
        userWithOkAge.setLogin("NewUser");
        userWithOkAge.setPassword("validPassword");
        userWithOkAge.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(userWithOkAge));
    }

    @Test
    void register_userWithNullAge_throwsCustomException() {
        User userWithNullAge = new User();
        userWithNullAge.setLogin("NewUser");
        userWithNullAge.setPassword("validPassword");

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithNullAge)
        );

        assertEquals("Age should not be null", ex.getMessage());
    }

    @Test
    void register_userWithExistingLogin_throwsCustomException() {
        User firstUser = new User();
        firstUser.setLogin("existingUser");
        firstUser.setPassword("validPassword");
        firstUser.setAge(25);
        Storage.people.add(firstUser);

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
    @Test
    void register_userWithNullLogin_throwsCustomException() {
        User userWithNullLogin = new User();
        userWithNullLogin.setPassword("validPassword");
        userWithNullLogin.setAge(25);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithNullLogin)
        );

        assertEquals("Login should not be null", ex.getMessage());
    }

    @Test
    void register_userWithNullPassword_throwsCustomException() {
        User userWithNullPassword = new User();
        userWithNullPassword.setLogin("NewUser");
        userWithNullPassword.setAge(25);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> registrationService.register(userWithNullPassword)
        );

        assertEquals("Password should not be null", ex.getMessage());
    }

    @Test
    void register_userWith8charPassword_ok() {
        User user = new User();
        user.setLogin("NewUser");
        user.setPassword("12345678");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
