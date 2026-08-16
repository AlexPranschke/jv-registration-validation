package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        User registeredUser = assertDoesNotThrow(() -> registrationService.register(validUser));

        assertNotNull(registeredUser);
        assertEquals(validUser, registeredUser);
        assertEquals(1, Storage.people.size());
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(null)
        );

        assertEquals("User should not be null", ex.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        User userWithShortPassword = new User();
        userWithShortPassword.setLogin("user1");
        userWithShortPassword.setPassword("123");
        userWithShortPassword.setAge(25);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithShortPassword)
        );

        assertEquals("Password should be at least 6 characters", ex.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User userWithShortLogin = new User();
        userWithShortLogin.setLogin("gsdde");
        userWithShortLogin.setPassword("validPassword");
        userWithShortLogin.setAge(25);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithShortLogin)
        );

        assertEquals("Login should be at least 6 characters", ex.getMessage());
    }

    @Test
    void register_8CharLogin_ok() {
        User userWith8CharLogin = new User();
        userWith8CharLogin.setLogin("gsddefgg");
        userWith8CharLogin.setPassword("validPassword");
        userWith8CharLogin.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(userWith8CharLogin));
    }

    @Test
    void register_userUnder18_notOk() {
        User underageUser = new User();
        underageUser.setLogin("NewUser");
        underageUser.setPassword("validPassword");
        underageUser.setAge(17);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(underageUser)
        );

        assertEquals("User should be at least 18 years old", ex.getMessage());
    }

    @Test
    void register_NegativeAge_notOk() {
        User userWithNegativeAge = new User();
        userWithNegativeAge.setLogin("NewUser");
        userWithNegativeAge.setPassword("validPassword");
        userWithNegativeAge.setAge(-5);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithNegativeAge)
        );

        assertEquals("User should be at least 18 years old", ex.getMessage());
    }

    @Test
    void register_Age18_ok() {
        User userWithOkAge = new User();
        userWithOkAge.setLogin("NewUser");
        userWithOkAge.setPassword("validPassword");
        userWithOkAge.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(userWithOkAge));
    }

    @Test
    void register_NullAge_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setLogin("NewUser");
        userWithNullAge.setPassword("validPassword");

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithNullAge)
        );

        assertEquals("Age should not be null", ex.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setPassword("validPassword");
        userWithNullLogin.setAge(25);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithNullLogin)
        );

        assertEquals("Login should not be null", ex.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setLogin("NewUser");
        userWithNullPassword.setAge(25);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithNullPassword)
        );

        assertEquals("Password should not be null", ex.getMessage());
    }

    @Test
    void register_8charPassword_ok() {
        User user = new User();
        user.setLogin("NewUser");
        user.setPassword("12345678");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ExistingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("existingUser");
        firstUser.setPassword("validPassword");
        firstUser.setAge(25);
        Storage.people.add(firstUser);

        User secondUser = new User();
        secondUser.setLogin("existingUser");
        secondUser.setPassword("anotherPassword");
        secondUser.setAge(30);

        RegistrationException ex = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(secondUser)
        );
        assertEquals("User with login existingUser already exists", ex.getMessage());
    }
}
