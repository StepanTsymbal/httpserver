package hazelserver.com.styopik.dao.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import hazelserver.com.styopik.dao.UserDao;
import hazelserver.com.styopik.model.User;

public class UserDaoImpl implements UserDao {

    private static final int MAX_STORAGE_CAPACITY = 20;

    private Config config = new Config();
    private HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

    private Map<Integer, List<User>> usersIdMap = instance.getMap("usersId");
    private Map<Integer, List<User>> usersLevelMap = instance.getMap("usersLevel");

    private List<User> usersIdList;
    private List<User> usersLevelList;

    public void putUser(User user) {
        usersIdList = (usersIdMap.get(user.getId()) != null) ? usersIdMap.get(user.getId()) : new LinkedList<User>();
        usersLevelList = (usersLevelMap.get(user.getLevel()) != null) ? usersLevelMap.get(user.getLevel()) : new LinkedList<User>();

        usersIdList.remove(user);
        usersIdList.add(user);
        usersLevelList.remove(user);
        usersLevelList.add(user);
        Collections.sort(usersIdList);
        Collections.sort(usersLevelList);

        if (usersIdList.size() > MAX_STORAGE_CAPACITY) {
            addDataToMap(usersIdList, usersLevelList, user);
        } else if (usersLevelList.size() > MAX_STORAGE_CAPACITY) {
            addDataToMap(usersLevelList, usersIdList, user);
        } else {
            usersIdMap.put(user.getId(), usersIdList);
            usersLevelMap.put(user.getLevel(), usersLevelList);
        }
    }
    
    private void addDataToMap(List<User> list1, List<User> list2, User user) {
        User tempUser = list1.get(list1.size() - 1);

        list1.remove(list1.size() - 1);
        list2.remove(tempUser);
        usersIdMap.replace(user.getId(), list1);
        usersLevelMap.replace(user.getLevel(), list2);
    }

    public List<User> getUsersById(int id) {

        return usersIdMap.get(id);
    }

    public List<User> getUserResultsByLevel(int level) {

        return usersLevelMap.get(level);
    }

}
