package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new CustomException("Incorrect data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with login " + user.getLogin() + " already exists");
        }
        if (user.getPassword().length() < 6) {
            throw new CustomException("Password should be at least 6 characters");
        }
        if (user.getLogin().length() < 4) {
            throw new CustomException("Login should be at least 4 characters");
        }
        if (user.getAge() < 18) {
            throw new CustomException("User should over least 18 years old");
        }
        return storageDao.add(user);
    }
}
