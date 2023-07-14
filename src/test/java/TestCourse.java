import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.model.Course;
import myy803.project2022.model.StudentRegistration;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class TestCourse {
	
	Course course;
	
	@BeforeEach
	public void setup() {
		course = new Course();
	}
	
	@Test
	public void testAddStudentRegistration() throws Exception {
		
		StudentRegistration student1 = new StudentRegistration();
		StudentRegistration student2 = new StudentRegistration();
		course.addStudentRegistrations(student1);
		course.addStudentRegistrations(student2);
		
		/*
		 *We took an empty list and we added two students
		 *So we expect the size to be 2
		 */
		assertEquals(2,course.getStudentRegistrations().size());
	}
	
	@Test
	public void testRemoveStudentRegistration() throws Exception {
		
		StudentRegistration student1 = new StudentRegistration();
		StudentRegistration student2 = new StudentRegistration();
		course.addStudentRegistrations(student1);
		course.addStudentRegistrations(student2);
		
		course.removeStudentRegistrations(student2);
		assertEquals(1,course.getStudentRegistrations().size());
	}
	
	@Test
	public void testSettersandGetters() throws Exception {
		
		course.setId(803);
		course.setCoursename("Software Engineering");
		course.setPeriod("2021-2022");
		course.setSemester("8");
		course.setCtype("Basic");
		course.setEcts("7");
		course.setProjectper("40");
		course.setExamper("60");
		
		assertEquals(803,course.getId());
		assertEquals("Software Engineering",course.getCoursename());
		assertEquals("2021-2022",course.getPeriod());
		assertEquals("8",course.getSemester());
		assertEquals("Basic",course.getCtype());
		assertEquals("7",course.getEcts());
		assertEquals("40",course.getProjectper());
		assertEquals("60",course.getExamper());
	}
}
