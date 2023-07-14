import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.dao.CourseDAO;
import myy803.project2022.dao.StudentRegistrationDAO;
import myy803.project2022.model.Course;
import myy803.project2022.model.StudentRegistration;
@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class TestStudentRegistrationDAO {
	
	@Autowired 
	StudentRegistrationDAO srDAO;
	
	@Autowired 
	CourseDAO courseDAO;
	
	public void clearDataBase() {
		/*CLEAR DATABASE BEFORE TEST RUNNING*/
		for(Course obj : courseDAO.findAll()) {
			List<StudentRegistration> students = obj.getStudentRegistrations();
			for(StudentRegistration s : students) {
				srDAO.deleteById(s.getId());
			}
		}
		courseDAO.deleteAll();
	}
	
	@Test
	public void testAStudentRegistrationDAOIsNotNull() {
		Assertions.assertNotNull(srDAO);
	}
	
	@Test
	public void testBCourseDAOIsNotNull() {
		Assertions.assertNotNull(courseDAO);
	}
	
	@Test
	public void testCFindRegistrationsByCourseId() {
		this.clearDataBase();
		Course c1 = new Course(1, "Software Engineering", "8","2021-2022","7","Basic","30","70");
		Course c2 = new Course(2, "Compilers", "8","2021-2022","7","Basic","30","70");
		StudentRegistration s1 = new StudentRegistration(1,"4292","Eirini Thoma","2022","8","5","5","5");
		StudentRegistration s2 = new StudentRegistration(2,"4327","Nikolaos Vouronikos","2022","8","5","5","5");
		c1.addStudentRegistrations(s1);
		s1.setCourse(c1);
		c2.addStudentRegistrations(s2);
		s2.setCourse(c2);
		srDAO.save(s1);
		srDAO.save(s2);
		List<StudentRegistration> students1 = srDAO.findRegistrationsByCourseId(courseDAO.findAll().get(0).getId());
		List<StudentRegistration> students2 = srDAO.findRegistrationsByCourseId(courseDAO.findAll().get(1).getId());
		Assertions.assertEquals(1, students1.size());
		Assertions.assertEquals(1, students2.size());
	}
	
	@Test
	public void testDFindByIdReturnsStudentRegistration() {
		StudentRegistration s1 = srDAO.findById(srDAO.findRegistrationsByCourseId(courseDAO.findAll().get(0).getId()).get(0).getId());
		Assertions.assertNotNull(s1);
		Assertions.assertEquals("Eirini Thoma", s1.getStudentname());
	}

}
