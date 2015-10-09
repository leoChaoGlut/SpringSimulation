package glut.service;

import glut.annotation.Component;
import glut.annotation.Resource;
import glut.dao.IStudentDAO;
import glut.dao.ITeacherDAO;

@Component
public class StudentServiceImp implements IStudentService {
	@Resource("studentDAOImp")
	private IStudentDAO sd;

	@Resource("teacherDAOImp")
	private ITeacherDAO td;

	@Override
	public void save() {
		// TODO Auto-generated method stub
		// 通过该servvice调用sd,td两个dao.
		sd.save();
		td.save();
	}

}
