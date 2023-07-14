import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.controller.CoursesManagmentAppController;
import myy803.project2022.model.Course;
import myy803.project2022.model.StudentRegistration;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.MethodName.class)
@AutoConfigureMockMvc
public class TestCoursesManagmentAppController {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CoursesManagmentAppController controller;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	
	public void clearDataBase() {
		
		/*CLEAR DATABASE BEFORE TEST RUNNING*/
		for(Course obj : controller.getCourseService().findAll()) {
			List<StudentRegistration> students = obj.getStudentRegistrations();
			for(StudentRegistration s : students) {
				controller.getStudentregistrationService().deleteById(s.getId());
			}
		}
		controller.getCourseService().deleteAll();
	}
	
	@Test
	public void testACoursesManagmentAppControllerIsNotNull() {
		Assertions.assertNotNull(controller);
	}
	
	@Test
	public void testBMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	/*Test : USER STORY 1*/
	@Test
	public void testCLoginPage() throws Exception{
		this.mockMvc.perform(get("/login"))
					.andExpect(status().isOk())
					.andExpect(view().name("login"));
	}
	
	/*Test : USER STORY 2*/
	@Test
	void testDListCourses() throws Exception {
		
		this.clearDataBase();
		
		this.mockMvc.perform(get("/courses/list"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("courseobj"))
					.andExpect(view().name("courses/list"));
		
		/*Create two courses and add them to the table*/
		Course c1 = new Course(1, "Networks 2", "8","2021-2022","6.5","Basic","40","60");	
		Course c2 = new Course(2, "Software Engineering", "8","2021-2022","7","Basic","30","70");
		controller.getCourseService().save(c1);
		controller.getCourseService().save(c2);
		
		/*Test if the two courses are in the list(by name)*/
		List<Course> courses = controller.getCourseService().findAll();
		assertEquals("Networks 2",courses.get(0).getCoursename());
		assertEquals("Software Engineering",courses.get(1).getCoursename());
	}
	
	/*Test : USER STORY 3*/
	@Test
	void testECourseFormAndAddSuccess() throws Exception {
		
		/*First test if courseForm html is returned with Add Course action*/
		this.mockMvc.perform(get("/courses/courseForm"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("courseobj"))
					.andExpect(view().name("courses/courseForm"));
		
		/*reset data base*/
		this.clearDataBase();
		
		/*Add a new course*/
		Course c1 = new Course(1, "Compilers", "8","2021-2022","6.5","Basic","40","60");
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(c1.getId()));
	    multiValueMap.add("coursename", c1.getCoursename());
	    multiValueMap.add("semester", c1.getSemester());
	    multiValueMap.add("period", c1.getPeriod());
	    multiValueMap.add("ects", c1.getEcts());
	    multiValueMap.add("ctype", c1.getCtype());
	    multiValueMap.add("projectper", c1.getProjectper());
	    multiValueMap.add("examper", c1.getExamper());
	    
		this.mockMvc.perform(post("/courses/courseAddSuccess")
			    	.params(multiValueMap))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/courseAddSuccess"));
		
		/*After correctly showing the courseAddSuccess html we should check if the list has the new course*/
		/*remember that size was 0 because of the reset we did*/
		List<Course> courses = controller.getCourseService().findAll();
		assertEquals(1,courses.size());
	}
	
	/*Test : USER STORY 5*/
	@Test
	public void testFUpdateCourse() throws Exception {
		
		/*reset data base*/
		this.clearDataBase();
		
		/*Create a new course and add it to the db*/
		Course c1 = new Course(1, "Compilers", "8","2021-2022","6.5","Basic","40","60");
		controller.getCourseService().save(c1);
		
		/*find our course's id*/
		int id = controller.getCourseService().findByName("Compilers").getId();
		
		this.mockMvc.perform(get("/courses/showFormForUpdate?courseId="+Integer.toString(id)))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/courseForm"));
		
		/*Test update for projectper and examper*/
		/*From 40-60 to 30-70*/
		c1 = controller.getCourseService().findById(id);
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(c1.getId()));
	    multiValueMap.add("coursename", c1.getCoursename());
	    multiValueMap.add("semester", c1.getSemester());
	    multiValueMap.add("period", c1.getPeriod());
	    multiValueMap.add("ects", c1.getEcts());
	    multiValueMap.add("ctype", c1.getCtype());
	    multiValueMap.add("projectper", "30");
	    multiValueMap.add("examper", "70");
	    
		this.mockMvc.perform(post("/courses/courseAddSuccess")
			    	.params(multiValueMap))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/courseAddSuccess"));
		
		List<Course> courses = controller.getCourseService().findAll();
		
		/*Check for updated fields and for one of the other fields(coursename) if it is still there*/
		assertEquals("30",controller.getCourseService().findById(id).getProjectper());
		assertEquals("70",controller.getCourseService().findById(id).getExamper());
		assertEquals("Compilers",courses.get(0).getCoursename());
		/*Be sure that app is updating and not adding again the course*/
		assertEquals(1,courses.size());
	}
	
	/*Test : USER STORY 4*/
	@Test
	public void testGDeleteCourse() throws Exception {
		
		int id = controller.getCourseService().findByName("Compilers").getId();
		
		this.mockMvc.perform(get("/courses/delete?courseId="+Integer.toString(id)))
					.andExpect(status().isFound())
					.andExpect(view().name("redirect:/courses/list"));
		
		/*size was 1 before deletion*/
		List<Course> courses = controller.getCourseService().findAll();
		assertEquals(0,courses.size());
	}
	
	/*Test Delete All utility*/
	@Test
	public void testHDeleteAllCourses() throws Exception {
		
		/*Create two courses and add them to the table*/
		Course c1 = new Course(1, "Networks 2", "8","2021-2022","6.5","Basic","40","60");	
		Course c2 = new Course(2, "Software Engineering", "8","2021-2022","7","Basic","30","70");
		controller.getCourseService().save(c1);
		controller.getCourseService().save(c2);
		
		this.mockMvc.perform(get("/courses/reset"))
					.andExpect(status().isFound())
					.andExpect(view().name("redirect:/courses/list"));
		
		/*Here we check always for size = 0*/
		List<Course> courses = controller.getCourseService().findAll();
		assertEquals(0,courses.size());
	}	
	
	/*Test : USER STORY 6*/
	@Test
	public void testIListStudentRegistrations() throws Exception {
		
		/*Create a new course and add it to the db*/
		Course c1 = new Course(1, "Compilers", "8","2021-2022","6.5","Basic","40","60");
		controller.getCourseService().save(c1);
		int id = controller.getCourseService().findByName("Compilers").getId();
		
		/*Then check if the html page is returned after Students Info action*/
		this.mockMvc.perform(get("/courses/showStudentRegistrations?courseId="+Integer.toString(id)))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("studentobj"))
					.andExpect(view().name("courses/studentsRegList"));
	}
	
	/*Test : USER STORY 7*/
	@Test
	public void testJStudentFormAndAddSuccess() throws Exception {
		
		int id = controller.getCourseService().findByName("Compilers").getId();
		
		/*First test if studentForm html is returned with Add Student action*/
		this.mockMvc.perform(get("/courses/studentForm"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("studentobj"))
					.andExpect(view().name("courses/studentForm"));
		
		StudentRegistration s1 = new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","5","5","5");
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(s1.getId()));
	    multiValueMap.add("am", s1.getAm());
	    multiValueMap.add("studentname", s1.getStudentname());
	    multiValueMap.add("yearofreg", s1.getYearofreg());
	    multiValueMap.add("currsemester", s1.getCurrsemester());
	    multiValueMap.add("projectgrade", s1.getProjectgrade());
	    multiValueMap.add("examgrade", s1.getExamgrade());
	    multiValueMap.add("finalgrade", s1.getFinalgrade());
	    
		this.mockMvc.perform(post("/courses/studentAddSuccess")
			    	.params(multiValueMap))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("studentobj"))
					.andExpect(view().name("courses/studentAddSuccess"));
		
		/*After correctly showing the studentcourseAddSuccess html we should check if the list has the new student*/
		/*remember that size was 0*/
		List<StudentRegistration> students = controller.getStudentregistrationService().findRegistrationsByCourseId(id);
		assertEquals(1,students.size());
	}
	
	/*Test : USER STORY 8*/
	@Test
	public void testNStudentDelete() throws Exception {
		
		int id = controller.getCourseService().findByName("Compilers").getId();
		int sid = controller.getStudentregistrationService().findRegistrationsByCourseId(id).get(0).getId();
		
		this.mockMvc.perform(get("/courses/delete2?studentId="+Integer.toString(sid)))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/confirm"));
		
		List<StudentRegistration> students = controller.getStudentregistrationService().findRegistrationsByCourseId(id);
		assertEquals(0,students.size());
	}
	
	/*Test : USER STORY 9*/
	@Test
	public void testKStudentUpdate() throws Exception {
		
		/*Here we should check only if the right page is returned
		 *Rest proccess is identical to student addition  
		 */
		Course c1 = controller.getCourseService().findAll().get(0);
		int sid = controller.getStudentregistrationService().findRegistrationsByCourseId(c1.getId()).get(0).getId();
		
		/*Finaly check if the right html is returned(studentForm)*/
		this.mockMvc.perform(get("/courses/showFormForUpdate2?studentId="+Integer.toString(sid)))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("studentobj"))
					.andExpect(view().name("courses/studentForm"));
	}
	
	/*Test : USER STORY 10*/
	@Test
	public void testLRegisterGradesPageSuccess() throws Exception {
		
		Course c1 = controller.getCourseService().findAll().get(0);
		int sid = controller.getStudentregistrationService().findRegistrationsByCourseId(c1.getId()).get(0).getId();
		
		this.mockMvc.perform(get("/courses/register?studentId="+Integer.toString(sid)))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("studentobj"))
					.andExpect(view().name("courses/registerGrades"));
	}
	
	/*Test : USER STORY 11*/
	@Test
	public void testMRegisterFinalGradesSuccess() throws Exception {
		
		Course c1 = controller.getCourseService().findByName("Compilers");
		StudentRegistration s1 = controller.getStudentregistrationService().findRegistrationsByCourseId(c1.getId()).get(0);
		
		assertTrue((Double.parseDouble(s1.getProjectgrade()) >= 0));
		assertTrue((Double.parseDouble(s1.getProjectgrade()) <= 10));
		assertTrue((Double.parseDouble(s1.getExamgrade()) >= 0));
		assertTrue((Double.parseDouble(s1.getExamgrade()) <= 10));
		/*Further tests for the right final grade result will be implemented in student service tests*/ 
	}
	
	/*Test for Show All Grades utility*/
	@Test
	public void testOShowGradesPage() throws Exception {
		
		this.mockMvc.perform(get("/courses/grades"))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/Grades"));
	}
	
	/*Tests for the statistics menus*/
	@Test
	public void testPShowProjectStatisticsMenu() throws Exception {
		
		this.mockMvc.perform(get("/courses/projectmenu"))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/menu"));
		
		assertEquals(0,controller.getMode());
	}
	
	@Test
	public void testQShowExamStatisticsMenu() throws Exception {
		
		this.mockMvc.perform(get("/courses/exammenu"))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/menu"));
		
		assertEquals(1,controller.getMode());
	}
	
	@Test
	public void testRShowFinalGradesStatisticsMenu() throws Exception {
		
		this.mockMvc.perform(get("/courses/finalmenu"))
					.andExpect(status().isOk())
					.andExpect(view().name("courses/menu"));
		
		assertEquals(2,controller.getMode());
	}
	
	/*Statistics Tests
	 *Here we should check only if the right html template is returned
	 *Functionality is tested in service tests
	 */
	
	@Test
	public void testShowMaxGradeSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/maxgrade"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showMax"));
	}
	
	@Test
	public void testShowMinGradeSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/mingrade"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showMin"));
	}
	
	@Test
	public void testShowMeanGradeSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/mean"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showMean"));
	}
	
	@Test
	public void testShowMedianGradeSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/median"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showMedian"));
	}
	
	@Test
	public void testShowStandardDeviationSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/sdev"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showSdev"));
	}
	
	@Test
	public void testShowVarianceSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/variance"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showVariance"));
	}
	
	@Test
	public void testShowSuccessPercentage() throws Exception {
		
		this.mockMvc.perform(get("/courses/successpercentage"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showSper"));
	}
	
	@Test
	public void testShowSkewnessSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/skewness"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showSkewness"));
	}
	
	@Test
	public void testShowKurtosisSuccess() throws Exception {
		
		this.mockMvc.perform(get("/courses/kurtosis"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("course"))
					.andExpect(view().name("courses/showKurtosis"));
	}
	
	/*Test for logout page*/
	@Test
	public void testLogoutPage() throws Exception{
		
		this.mockMvc.perform(get("/courses/logout"))
					.andExpect(status().isFound())
					.andExpect(view().name("redirect:/"));
	}
}
