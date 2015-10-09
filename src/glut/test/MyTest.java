package glut.test;

import glut.dao.IStudentDAO;
import glut.dao.ITeacherDAO;
import glut.service.IStudentService;
import glut.spring.BeanFactory;
import glut.spring.DynamicProxy;

import org.junit.Test;

public class MyTest {
	@Test
	public void test() {
		// pkgToScan:要扫描的包路径,多个同前缀的包会递归处理
		String pkgToScan = "glut";
		BeanFactory bf = new BeanFactory(pkgToScan);

		IStudentService iss = bf.getInstance("studentServiceImp");
		// AOP绑定
		IStudentDAO sd = bf.getInstance("studentDAOImp");
		ITeacherDAO td = bf.getInstance("teacherDAOImp");

		iss = DynamicProxy.bind(iss);
		td = DynamicProxy.bind(td);
		iss.save();

	}
}
