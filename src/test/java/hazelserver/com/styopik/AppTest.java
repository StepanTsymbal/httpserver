package hazelserver.com.styopik;

import hazelserver.com.styopik.model.User;

import junit.framework.TestCase;

public class AppTest extends TestCase {

    public void testUsersEquality() {
    	User user1 = new User(1, 2, 3.0);
    	User user2 = new User();
    	User user3 = new User(1, 2, 10);

    	user2.setId(1);
    	user2.setLevel(2);
    	user2.setResult(3.0);

    	assertEquals(user1, user2);
    	assertEquals(user1, user3);
    }

}
