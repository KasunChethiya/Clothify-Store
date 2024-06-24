package edu.icet.bo.user;

import edu.icet.dao.user.UserDaoImpl;
import edu.icet.entity.UserEntity;

import java.util.List;

public class SettingsService {
    UserDaoImpl userDaoImpl = new UserDaoImpl();
    public List<UserEntity> loadAccountDetails(String emailFromDatabase) {
        List<UserEntity> userEntities = userDaoImpl.retriveAcount(emailFromDatabase);
        return userEntities;
    }

    public void updatePassword(String newPassword) {
        String emailFromDatabase = SignInService.emailFromDatabase;
        userDaoImpl.updateUserAccount(emailFromDatabase, newPassword);
    }
}
