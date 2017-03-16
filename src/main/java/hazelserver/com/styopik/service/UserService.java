package hazelserver.com.styopik.service;

import java.util.List;

import hazelserver.com.styopik.model.User;

public interface UserService {

    void putUser(User user);
    List<User> getUsersById(int id);
    List<User> getUserResultsByLevel(int level);

}
