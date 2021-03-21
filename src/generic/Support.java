package generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Support {
	
	enum Action{
		CREATE("CreatedBy");

		Action(String string) {
			// TODO Auto-generated constructor stub
		}
	}

		public static void updateUser(User user, Class<?> cls) {
			try {
				Method method = cls.getMethod("setUserName", String.class);
				method.invoke(user, "Zakaria");
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		public static void main(String[] args) {
			User user = new User();
			updateUser(user, User.class);
			System.out.println(user.getUserName());
		}
		
//		public static <T> T get(String name) {
//			
//			if(true) {
//				return (T) new String();
//			}else {
//				return (T) new ArrayList();
//			}
//		}
}
