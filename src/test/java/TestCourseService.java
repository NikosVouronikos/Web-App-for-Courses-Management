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
import myy803.project2022.service.CourseService;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestCourseService {
	
	@Autowired 
	CourseService courseService;
	
	public void clearDataBase() {
		courseService.deleteAll();
	}
	
	@Test
	public void testACourseServiceIsNotNull() {
		Assertions.assertNotNull(courseService);
	}
	
	@Test
	public void testCFindAllReturnsCourses() {
		List<Course> courses = courseService.findAll();
		Assertions.assertNotNull(courses);
		Assertions.assertEquals(2, courses.size());
	}
	
	@Test
	public void testDFindByIdReturnsCourse() {
		Course course = courseService.findById(courseService.findAll().get(0).getId());
		Assertions.assertNotNull(course);
		Assertions.assertEquals("Compilers", course.getCoursename());
	}
	
	@Test
	public void testEFindByNameReturnsCourse() {
		Course course = courseService.findByName("Software Engineering");
		Assertions.assertNotNull(course);
		Assertions.assertEquals("Software Engineering", course.getCoursename());
	}
	
	@Test
	public void testBSaveCourse() {
		this.clearDataBase();
		Course c1 = new Course(1, "Compilers", "8","2021-2022","7","Basic","40","60");
		Course c2 = new Course(2, "Software Engineering", "8","2021-2022","7","Basic","30","70");
		courseService.save(c1);
		courseService.save(c2);
		List<Course> courses = courseService.findAll();
		c1.setId(courseService.findAll().get(0).getId());
		c2.setId(courseService.findAll().get(1).getId());
		Assertions.assertEquals(2,courses.size());
	}
	
	@Test
	public void testFDeleteById() {
		courseService.deleteById(courseService.findAll().get(0).getId());
		List<Course> courses = courseService.findAll();
		Assertions.assertEquals(1,courses.size());
	}
	
	@Test
	public void testGDeleteAll() {
		courseService.deleteAll();
		List<Course> courses = courseService.findAll();
		Assertions.assertEquals(0,courses.size());
	}

}