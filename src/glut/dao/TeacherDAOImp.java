package glut.dao;

import glut.annotation.Component;

@Component
public class TeacherDAOImp implements ITeacherDAO {

	@Override
	public void save() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getSimpleName() + " save");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("UserDAOImp update");
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("UserDAOImp delete");
	}

}
