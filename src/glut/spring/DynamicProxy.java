package glut.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
	private Object target;

	public DynamicProxy() {
		// TODO Auto-generated constructor stub
	}

	public DynamicProxy(Object target) {
		super();
		this.target = target;
	}

	public static <T> T bind(Object obj) {
		Class<? extends Object> clz = obj.getClass();
		return (T) Proxy.newProxyInstance(clz.getClassLoader(),
				clz.getInterfaces(), new DynamicProxy(obj));

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		before(proxy, method, args);

		Object invoke = method.invoke(target, args);

		after(proxy, method, args);

		return invoke;
	}

	private void before(Object proxy, Method method, Object[] args) {
		// TODO Auto-generated method stub
		System.out.println("Before:" + " Class Name:"
				+ target.getClass().getSimpleName() + "     method name:"
				+ method.getName());
	}

	private void after(Object proxy, Method method, Object[] args) {
		// TODO Auto-generated method stub
		System.out.println("After :" + " Class Name:"
				+ target.getClass().getSimpleName() + "     method name:"
				+ method.getName());
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
