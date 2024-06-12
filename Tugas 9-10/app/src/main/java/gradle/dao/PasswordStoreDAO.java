package gradle.dao;

import gradle.entities.PasswordStores;
import gradle.entities.UserData;
import java.util.*;

public interface PasswordStoreDAO {
    int addPassword(PasswordStores newPassword, UserData user);

    ArrayList<PasswordStores> listPassword(UserData user);

    ArrayList<PasswordStores> findPassword(String name, UserData user);

    int updatePass(PasswordStores changedPass);

    int deletePass(PasswordStores deletedPass);
}