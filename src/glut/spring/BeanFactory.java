package glut.spring;

import glut.annotation.Component;
import glut.annotation.Resource;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
	/**
	 * pkg:package to scan;
	 */
	private String pkg;
	/**
	 * 将Component注解的value返回值作为key,对应的类作为value,存放在instanceMap中
	 */
	private Map<String, Object> instanceMap = new HashMap<>();

	/**
	 * 如果使用无参构造器,之后请记得使用setPkg方法
	 */
	public BeanFactory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 调用该构造器会回调ioc
	 * 
	 * @param pkg
	 */
	public BeanFactory(String pkg) {
		this.pkg = pkg;
		IoC();
	}

	private void IoC() {
		// 将包形式转换为文件路径形式
		String pkgPath = pkg.replaceAll("\\.", "/");
		// 获取当前project的绝对路径
		String projectPath = BeanFactory.class.getResource("/").toString();
		// 对获取的路径进行处理,去掉开头的file:/
		projectPath = projectPath.substring(projectPath.indexOf("/") + 1);
		// 获取指定路径下的所有文件

		// 遍历所有的文件和文件夹
		recursion(new File(projectPath + pkgPath));

		findResource();
	}

	// 将instanceMap里所有的实例的filed找出,看看是否存在@Resource,如果存在就将它实例化
	private void findResource() {
		// TODO Auto-generated method stub
		// 获取指定路径pkg下存在@Component的所有对象实例
		Collection<Object> values = instanceMap.values();
		for (Object obj : values) {
			try {
				initResource(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化带有@Resource的field
	 * 
	 * @param obj
	 *            field的拥有者
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void initResource(Object obj) throws IllegalArgumentException,
			IllegalAccessException {
		// TODO Auto-generated method stub
		Class<? extends Object> clz = obj.getClass();
		Field[] fields = clz.getDeclaredFields();
		for (Field f : fields) {
			Class<Resource> res = Resource.class;
			if (f.isAnnotationPresent(res)) {
				Resource r = f.getAnnotation(res);
				String resName = r.value();
				Object o = instanceMap.get(resName);
				f.setAccessible(true);
				f.set(obj, o);
			}
		}
	}

	/**
	 * 递归寻找指定路径下的所有类文件
	 * 
	 * @param file
	 */
	private void recursion(File file) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				fillMap(f);
			} else {
				recursion(f);
			}
		}
	}

	/**
	 * 对找到类文件进行处理,将该类文件头上的注解解析,将对应信息存放到map中
	 * 
	 * @param f
	 */
	private void fillMap(File f) {
		// 类的绝对路径
		String absolutePath = f.getAbsolutePath();
		// 包路径
		String pkgPath = absolutePath.replaceAll("\\\\", ".");
		// 获取类路径,并去掉文件格式
		String objPath = pkgPath.substring(pkgPath.indexOf(pkg),
				pkgPath.length() - 6);
		try {
			// 通过类路径创建指定的类
			Class<?> clz = Class.forName(objPath);

			Class<Component> annoClz = Component.class;

			Class<Resource> res = Resource.class;

			if (clz.isAnnotationPresent(annoClz)) {

				Component c = clz.getAnnotation(annoClz);

				Object instance = clz.newInstance();

				String instanceName = "";

				String annoValue = c.value();
				// 如果使用的是默认值,则以类名为key存入map
				if ("".equals(annoValue)) {
					instanceName = instance.getClass().getSimpleName();
					// 将类名第一个字母改成小写
					instanceName = (instanceName.charAt(0) + "").toLowerCase()
							+ instanceName.substring(1);
				} else {
					instanceName = annoValue;
				}

				// 存放到map中,如果返回值不为空,则表明已存在同名实例.
				if (instanceMap.put(instanceName, instance) != null) {
					throw new Exception("存在同名实例");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, Object> getInstances() {
		return instanceMap;
	}

	/**
	 * 根据实例名获取实例
	 * 
	 * @param instanceName
	 *            实例名
	 * @return 实例
	 */
	public <T> T getInstance(String instanceName) {
		return (T) instanceMap.get(instanceName);
	}

	public String getPkg() {
		return pkg;
	}

	/**
	 * 调用set方法会回调IoC
	 * 
	 * @param pkg
	 */
	public void setPkg(String pkg) {
		this.pkg = pkg;
		IoC();
	}

}
