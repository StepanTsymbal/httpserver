package hazelserver.com.styopik.dao;

import java.util.List;

import hazelserver.com.styopik.model.User;

public interface UserDao {

    void putUser(User user);
    List<User> getUsersById(int id);
    List<User> getUserResultsByLevel(int level);

}
