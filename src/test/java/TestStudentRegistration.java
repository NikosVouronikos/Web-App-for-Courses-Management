import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.model.StudentRegistration;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class TestStudentRegistration {
	
	StudentRegistration sr;
	
	@BeforeEach
	public void setup() {
		sr = new StudentRegistration();
	}
	
	@Test 
	public void testSettersAndGetters() throws Exception {
		
		sr.setId(4327);
		sr.setAm("4327");
		sr.setStudentname("Nikolaos Vouronikos");
		sr.setCurrsemester("8");
		sr.setYearofreg("2022");
		sr.setProjectgrade("8");
		sr.setExamgrade("6");
		sr.setFinalgrade("6.8");
		
		assertEquals(4327,sr.getId());
		assertEquals("4327",sr.getAm());
		assertEquals("Nikolaos Vouronikos",sr.getStudentname());
		assertEquals("8",sr.getCurrsemester());
		assertEquals("2022",sr.getYearofreg());
		assertEquals("8",sr.getProjectgrade());
		assertEquals("6",sr.getExamgrade());
		assertEquals("6.8",sr.getFinalgrade());
	}
	
}
