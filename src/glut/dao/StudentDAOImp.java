package glut.dao;

import glut.annotation.Component;

@Component
public class StudentDAOImp implements IStudentDAO {

	@Override
	public void save() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getSimpleName() + " save");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("StudentDAOImp update");
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("StudentDAOImp delete");
	}

}
