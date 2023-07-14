package myy803.project2022.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import myy803.project2022.dao.CourseDAO;
import myy803.project2022.dao.StudentRegistrationDAO;
import myy803.project2022.model.StudentRegistration;

@Component
public class StudentRegistrationServiceImpl implements StudentRegistrationService {
	
	@Autowired
	private StudentRegistrationDAO StudentRegistrationRepository;
	
	@Autowired
	private CourseDAO CourseRepository;

	public StudentRegistrationServiceImpl() {
		super();
	}
	
	@Autowired
	public StudentRegistrationServiceImpl(StudentRegistrationDAO theStudentRegistrationDAO, CourseDAO theCourseDAO) {
		StudentRegistrationRepository = theStudentRegistrationDAO;
		CourseRepository = theCourseDAO;
	}

	@Override
	@Transactional
	public List<StudentRegistration> findRegistrationsByCourseId(int courseid) {
		
		if (!CourseRepository.existsById(courseid)) {
		      throw new RuntimeException("Not found Tutorial with id = " + courseid);
		}
		List<StudentRegistration> theSR = StudentRegistrationRepository.findRegistrationsByCourseId(courseid);
		return theSR;
	}

	@Override
	@Transactional
	public void save(StudentRegistration theSR) {
		StudentRegistrationRepository.save(theSR);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		StudentRegistrationRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public StudentRegistration findById(int theId) {
		StudentRegistration obj = StudentRegistrationRepository.findById(theId);
		if(obj != null) {
			return obj;
		}
		else {
			throw new RuntimeException("Dit not find course code -> " + theId);
		}
	}
	
	@Override
	@Transactional
	public StudentRegistration findByName(String studentname) {
		
		List<StudentRegistration> students = StudentRegistrationRepository.findAll();
		for(StudentRegistration obj : students) {
			if(obj.getStudentname().equals(studentname)) {
				return obj;
			}
		}
		throw new RuntimeException("Dit not find course with name -> " + studentname);
	}
	
	public double caculateFinalGrade(double projectper,double projectgrade,double examper,double examgrade) {
		
		double finalgr = (projectper/100)*projectgrade + (examper/100)*examgrade;
		return finalgr;
	}
	
	public StudentRegistrationDAO getStudentRegistrationRepository() {
		return StudentRegistrationRepository;
	}

	public void setStudentRegistrationRepository(StudentRegistrationDAO studentRegistrationRepository) {
		StudentRegistrationRepository = studentRegistrationRepository;
	}
	
	public CourseDAO getCourseRepository() {
		return CourseRepository;
	}

	public void setCourseRepository(CourseDAO courseRepository) {
		CourseRepository = courseRepository;
	}
}
