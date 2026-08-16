package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User should not be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login should not be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password should not be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age should not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password should be at least " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "Login should be at least " + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "User should be at least " + MIN_AGE + " years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with login " + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}
