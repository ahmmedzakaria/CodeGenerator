package generic;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		int x = 5;
		int y = x;
		y = y+1;
		System.out.println(x+": x");
		System.out.println(y+": y");
		
		User user1 = new User();
		user1.setId("1");
		
		User user2 = user1;
		user2.setId("2");
		System.out.println(user1.getId()+": user1");
		System.out.println(user2.getId()+": user2");
		
		List<User> userList1 = new ArrayList();
		User user3 = new User();
		user3.setId("3");
		userList1.add(user3);
		
		List<User> userList2 = new ArrayList();
		userList2.add(user3);
		userList2.get(0).setId("4");
		
		List<User> userList3 = new ArrayList();
		userList2.forEach(u->userList3.add(u));
		userList2.get(0).setId("5");
		System.out.println(userList1.get(0).getId()+": userList1");
		System.out.println(userList2.get(0).getId()+": userList2");
		System.out.println(userList3.get(0).getId()+": userList3");
		
		
		
	}
}
