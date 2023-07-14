import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.model.Course;
import myy803.project2022.model.StudentRegistration;
import myy803.project2022.service.CourseService;
import myy803.project2022.service.StudentRegistrationService;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestStudentRegistrationService {
	
	@Autowired 
	StudentRegistrationService srService;
	
	@Autowired 
	CourseService courseService;
	
	public void clearDataBase() {
		/*CLEAR DATABASE BEFORE TEST RUNNING*/
		for(Course obj : courseService.findAll()) {
			List<StudentRegistration> students = obj.getStudentRegistrations();
			for(StudentRegistration s : students) {
				srService.deleteById(s.getId());
			}
		}
		courseService.deleteAll();
	}
	
	@Test
	public void testAStudentRegistrationServiceIsNotNull() {
		Assertions.assertNotNull(srService);
	}
	
	@Test
	public void testBCourseServiceIsNotNull() {
		Assertions.assertNotNull(courseService);
	}
	
	@Test
	public void testDFindRegistrationsByCourseIdReturnsStudentRegistrations() {
		List<StudentRegistration> students = srService.findRegistrationsByCourseId(courseService.findAll().get(0).getId());
		Assertions.assertNotNull(students);
		Assertions.assertEquals(1, students.size());
	}
	
	@Test
	public void testFFindByIdReturnsStudentRegistration() {
		StudentRegistration s1 = srService.findById(srService.findRegistrationsByCourseId(courseService.findAll().get(0).getId()).get(0).getId());
		Assertions.assertNotNull(s1);
		Assertions.assertEquals("Eirini Thoma", s1.getStudentname());
	}
	
	@Test
	public void testGFindByNameReturnsStudentRegistration() {
		int id = courseService.findAll().get(0).getId();
		StudentRegistration s1 = srService.findRegistrationsByCourseId(id).get(0);
		Assertions.assertNotNull(s1);
		Assertions.assertEquals("Eirini Thoma", s1.getStudentname());
	}
	
	@Test
	public void testCSaveStudentRegistration() {
		this.clearDataBase();
		Course c1 = new Course(1, "Compilers", "8","2021-2022","7","Basic","40","60");
		StudentRegistration s1 = new StudentRegistration(1,"4292","Eirini Thoma","8","2022","6","5","");
		c1.addStudentRegistrations(s1);
		s1.setCourse(c1);
		srService.save(s1);
		Assertions.assertEquals(1,srService.findRegistrationsByCourseId(courseService.findAll().get(0).getId()).size());
	}
	
	@Test
	public void testIDeleteById() {
		srService.deleteById(srService.findRegistrationsByCourseId(courseService.findAll().get(0).getId()).get(0).getId());
		List<StudentRegistration> students = srService.findRegistrationsByCourseId(courseService.findAll().get(0).getId());
		Assertions.assertEquals(0,students.size());
	}
	
	@Test
	public void testHCalculateFinalGrade() {
		Course c1 = courseService.findAll().get(0);
		int id = courseService.findAll().get(0).getId();
		StudentRegistration s1 = srService.findRegistrationsByCourseId(id).get(0);
		double fgr = srService.caculateFinalGrade(Double.parseDouble(c1.getProjectper()), Double.parseDouble(s1.getProjectgrade()), 
													Double.parseDouble(c1.getExamper()), Double.parseDouble(s1.getExamgrade()));
		/*6*0.4+5*0.6 should be finalgrade = 5.4*/
		Assertions.assertEquals(fgr,5.4);
	}
}