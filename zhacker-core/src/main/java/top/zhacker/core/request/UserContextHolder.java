package top.zhacker.core.request;

/**
 * 用户上下文
 */
public class UserContextHolder {
	
	public static ThreadLocal<Operator> context = new ThreadLocal<Operator>();
	
	public static Operator currentUser() {
		return context.get();
	}

	public static void set(Operator user) {
		context.set(user);
	}

	public static void remove() {
		context.remove();
	}
	
}
