package myy803.project2022.service;

import java.util.List;

import org.springframework.stereotype.Service;
import myy803.project2022.model.StudentRegistration;

@Service
public interface StudentRegistrationService {
	
	public List<StudentRegistration> findRegistrationsByCourseId(int id);
	
	public void save(StudentRegistration theStudent);
	
	public void deleteById(int id);

	public StudentRegistration findById(int theId);
	
	public StudentRegistration findByName(String studentname);
	
	public double caculateFinalGrade(double projectper,double projectgrade,double examper,double examgrade);
}
