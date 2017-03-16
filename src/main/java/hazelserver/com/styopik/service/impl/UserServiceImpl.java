package hazelserver.com.styopik.service.impl;

import java.util.List;

import hazelserver.com.styopik.dao.UserDao;
import hazelserver.com.styopik.dao.impl.UserDaoImpl;
import hazelserver.com.styopik.model.User;
import hazelserver.com.styopik.service.UserService;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

	public void putUser(User user) {
        userDao.putUser(user);
    }

    public List<User> getUsersById(int id) {

        return userDao.getUsersById(id);
    }

    public List<User> getUserResultsByLevel(int level) {

        return userDao.getUserResultsByLevel(level);
    }
}
