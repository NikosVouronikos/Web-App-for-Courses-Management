package myy803.project2022.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myy803.project2022.model.StudentRegistration;

@Repository
public interface StudentRegistrationDAO extends JpaRepository<StudentRegistration, Integer> {
	
	public List<StudentRegistration> findRegistrationsByCourseId(int id);
	public StudentRegistration findById(int theId);
}
