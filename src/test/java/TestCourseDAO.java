import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.dao.CourseDAO;
import myy803.project2022.model.Course;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class TestCourseDAO {
	
	@Autowired 
	CourseDAO courseDAO;
	
	public void clearDataBase() {
		courseDAO.deleteAll();
	}
	
	@Test
	public void testCourseDAOIsNotNull() {
		Assertions.assertNotNull(courseDAO);
	}
	
	@Test
	public void testFindByIdReturnsCourse() {
		this.clearDataBase();
		Course c1 = new Course(1, "Software Engineering", "8","2021-2022","7","Basic","30","70");
		courseDAO.save(c1);
		c1.setId(courseDAO.findAll().get(0).getId());
		Course course = courseDAO.findById(c1.getId());
		Assertions.assertNotNull(course);
		Assertions.assertEquals(course.getId(), c1.getId());
		Assertions.assertEquals("Software Engineering", course.getCoursename());
	}

}
