package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CustomException("User should not be null");
        }
        if (user.getLogin() == null) {
            throw new CustomException("Login should not be null");
        }
        if (user.getPassword() == null) {
            throw new CustomException("Password should not be null");
        }
        if (user.getAge() == null) {
            throw new CustomException("Age should not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new CustomException("Password should be at least 6 characters");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new CustomException("Login should be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new CustomException("User should be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with login " + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}
