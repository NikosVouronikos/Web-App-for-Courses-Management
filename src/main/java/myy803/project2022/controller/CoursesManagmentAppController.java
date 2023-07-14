package myy803.project2022.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import myy803.project2022.model.Course;
import myy803.project2022.model.StudentRegistration;
import myy803.project2022.service.CourseService;
import myy803.project2022.service.StatisticStrategy;
import myy803.project2022.service.StudentRegistrationService;

@Controller
@RequestMapping("/courses")
public class CoursesManagmentAppController implements WebMvcConfigurer {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentRegistrationService studentregistrationService;
	
	@Autowired
	private StatisticStrategy statistic;
	
	private Course course;
	
	/*mode 0 : project
	 *mode 1 : exam
	 *mode 2 : final grades
	 */
	private int mode;
	
	/*Constructors*/
	public CoursesManagmentAppController() {
		super();
	}
	
	public CoursesManagmentAppController(CourseService cs, StudentRegistrationService srs, StatisticStrategy stats ,Course course, int mode) {
		courseService = cs;
		studentregistrationService = srs;
		statistic = stats;
		this.course = course;
		this.mode = mode;
	}
	
	/*Add view controllers for home and login pages*/ 
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login").setViewName("login");
	}
	
	/*Courses Managmnet*/
	
	@RequestMapping("/list")
	public String listCourses(Model theModel) {
		
		/*Access all the courses added to the DB*/
		List<Course> theCourses = courseService.findAll();
		
		/*Parse arraylist to list.html template*/
		theModel.addAttribute("courseobj", theCourses);
		
		return "courses/list";
	}	
	
	@RequestMapping("/courseForm")
	public String courseForm(Model theModel) {
		
		Course theCourse = new Course();
		
		/*Parse course object in order to take its fields from the html template(user input)*/
		theModel.addAttribute("courseobj", theCourse);
		
		return "courses/courseForm";
	}
	
	@RequestMapping("/courseAddSuccess")
	public String courseAddSuccess(@ModelAttribute("courseobj") Course obj, 
									Model theModel) {
		
		double projper = Double.parseDouble(obj.getProjectper());
		double examper = Double.parseDouble(obj.getExamper());
		double total = projper + examper;
		
		/*Check for wrong input*/
		if(projper < 0 || projper > 100 || examper < 0 || examper > 100 || total != 100) {
			return "redirect:/error";
		}
		/*Show Success page with user's input*/
		/*And insert course into our DB(Course table)*/
		courseService.save(obj);
		
		return "courses/courseAddSuccess";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("courseId") int theId) {
		
		/*Given id - delete course*/
		courseService.deleteById(theId);
		
		return "redirect:/courses/list";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("courseId") int theId,
									Model theModel) {
		
		/*Find course using id and update its columns*/
		Course theCourse = courseService.findById(theId);
		
		theModel.addAttribute("courseobj", theCourse);
		
		return "courses/courseForm";			
	}
	
	@RequestMapping("/reset")
	public String reset(Model theModel) {
		
		/*Clear course list*/
		for(Course obj : courseService.findAll()) {
			List<StudentRegistration> students = obj.getStudentRegistrations();
			for(StudentRegistration s : students) {
				studentregistrationService.deleteById(s.getId());
			}
		}
		courseService.deleteAll();
		
		return "redirect:/courses/list";		
	}
	
	/*Student Registrations Managmnet*/
	
	@RequestMapping("/showStudentRegistrations")
	public String listStudentsRegistrations(@RequestParam("courseId") int theId,
											Model theModel) {
		
		Course theCourse = courseService.findById(theId);
		this.setCourse(theCourse);
		
		List<StudentRegistration> theStReg = studentregistrationService.findRegistrationsByCourseId(theId);
		
		theModel.addAttribute("courseobj2", theCourse);
		theModel.addAttribute("studentobj", theStReg);
		
		return "courses/studentsRegList";
	}
	
	@RequestMapping("/studentForm")
	public String studentForm(Model theModel) {
			
		StudentRegistration theStudent = new StudentRegistration();

		theModel.addAttribute("studentobj", theStudent);
		
		return "courses/studentForm";
	}
	
	@RequestMapping("/studentAddSuccess")
	public String studentAddSuccess(@ModelAttribute("studentobj") StudentRegistration obj,
									Model theModel) {
		
		/*add student to course's List<StudentRegistration>*/
		this.getCourse().addStudentRegistrations(obj);
		/*and set the selected course for student object*/
		obj.setCourse(this.getCourse());

		studentregistrationService.save(obj);
		
		return "courses/studentAddSuccess";
	}
	
	@RequestMapping("/delete2")
	public String delete2(@RequestParam("studentId") int theId) {
		
		studentregistrationService.deleteById(theId);
		
		return "courses/confirm";
	}
	
	@RequestMapping("/showFormForUpdate2")
	public String showFormForUpdate2(@RequestParam("studentId") int theId,
									Model theModel) {
		
		StudentRegistration theSR = studentregistrationService.findById(theId);
		
		theModel.addAttribute("studentobj", theSR);
		
		return "courses/studentForm";			
	}
	
	@RequestMapping("/register")
	public String register(@RequestParam("studentId") int theId,
									Model theModel) {
		
		StudentRegistration theSR = studentregistrationService.findById(theId);
		
		theModel.addAttribute("studentobj", theSR);
		
		return "courses/registerGrades";			
	}
	
	@RequestMapping("/showFinalGrade")
	public String showFinalGrade(@ModelAttribute("studentobj") StudentRegistration obj,
									Model theModel) {
		
		double proj = Double.parseDouble(obj.getProjectgrade());
		double projper = Double.parseDouble(this.getCourse().getProjectper());
		double ex = Double.parseDouble(obj.getExamgrade());
		double exper = Double.parseDouble(this.getCourse().getExamper());
		
		/*catch wrong input*/
		if(proj > 10 || proj < 0 || ex > 10 || ex < 0) {
			return "redirect:/error";
		}
		
		double finalgr = studentregistrationService.caculateFinalGrade(projper, proj, exper, ex);
		DecimalFormat df = new DecimalFormat("#.##");
		String fgr = df.format(finalgr);
		
		obj.setProjectgrade(Double.toString(proj));
		obj.setExamgrade(Double.toString(ex));
		obj.setFinalgrade(fgr);
		
		obj.setCourse(this.getCourse());
		studentregistrationService.save(obj);
		
		return "courses/finalGradeSuccess";			
	}
	
	@RequestMapping("/grades")
	public String grades(Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		
		theSRS = this.getCourse().getStudentRegistrations();
		
		theModel.addAttribute("theSR", theSRS);
		
		return "courses/Grades";
	}
	
	@RequestMapping("/projectmenu")
	public String projectmenu(Model theModel) {
		
		this.setMode(0);
		theModel.addAttribute("course", this.getCourse());
		
		return "courses/menu";
	}
	
	@RequestMapping("/exammenu")
	public String exammenu(Model theModel) {
		
		this.setMode(1);
		theModel.addAttribute("course", this.getCourse());
		
		return "courses/menu";
	}
	
	@RequestMapping("/finalmenu")
	public String finalmenu(Model theModel) {
		
		this.setMode(2);
		theModel.addAttribute("course", this.getCourse());
		
		return "courses/menu";
	}
	
	@RequestMapping("/maxgrade")
	public String maxgrade(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		double max = statistic.MaxMin(theSRS,0,this.getMode());
		obj.setMaximum(max);
		
		return "courses/showMax";
	}	
	
	@RequestMapping("/mingrade")
	public String mingrade(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		double min = statistic.MaxMin(theSRS,1,this.getMode());
		obj.setMinimum(min);
		
		return "courses/showMin";
	}	
	
	@RequestMapping("/mean")
	public String mean(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		double mean = statistic.Mean(theSRS,this.getMode());
		obj.setMeangrade(mean);
		
		return "courses/showMean";
	}	
	
	@RequestMapping("/median")
	public String median(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double median = statistic.Median(theSRS,this.getMode());
		median = Double.valueOf(twoDForm.format(median));
		obj.setMediangrade(median);
		
		return "courses/showMedian";
	}
	
	@RequestMapping("/sdev")
	public String standardDeviation(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		DecimalFormat twoDForm = new DecimalFormat("#.####");
		double sdev = statistic.StandardDeviation(theSRS,this.getMode());
		sdev = Double.valueOf(twoDForm.format(sdev));
		obj.setSdeviation(sdev);
		
		return "courses/showSdev";
	}	
	
	@RequestMapping("/variance")
	public String Variance(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		DecimalFormat twoDForm = new DecimalFormat("#.####");
		double var = statistic.Variance(theSRS,this.getMode());
		var = Double.valueOf(twoDForm.format(var));
		obj.setVariance(var);
		
		return "courses/showVariance";
	}	
	
	@RequestMapping("/successpercentage")
	public String SuccessPercentage(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double percentage = statistic.SPercentage(theSRS,this.getMode());
		percentage = Double.valueOf(twoDForm.format(percentage));
		obj.setSper(percentage);
		
		return "courses/showSper";
	}
	
	@RequestMapping("/skewness")
	public String Skewness(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double sk = statistic.Skewness(theSRS,this.getMode());
		sk = Double.valueOf(twoDForm.format(sk));
		obj.setSkewness(sk);
	
		return "courses/showSkewness";
	}
	
	@RequestMapping("/kurtosis")
	public String Kurtosis(@ModelAttribute("course") Course obj,
							Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double kur = statistic.Kurtosis(theSRS,this.getMode());
		kur = Double.valueOf(twoDForm.format(kur));
		obj.setKurtosis(kur);
		
		return "courses/showKurtosis";
	}
	
	@RequestMapping("/resetStudents")
	public String resetStudents(@ModelAttribute("studentobj") StudentRegistration obj,
								Model theModel) {
		
		List<StudentRegistration> theSRS = new ArrayList<StudentRegistration>();
		theSRS = this.getCourse().getStudentRegistrations();
		
		for(int i = 0; i < theSRS.size(); i++) {
			studentregistrationService.deleteById(theSRS.get(i).getId());
		}
		return "courses/confirm";		
	}
	
	/*After logout disable going back and allow only login page*/ 
	@RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
		
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        
        return "redirect:/";
    }
	
	/*Setters and getters*/
	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public StudentRegistrationService getStudentregistrationService() {
		return studentregistrationService;
	}

	public void setStudentregistrationService(StudentRegistrationService studentregistrationService) {
		this.studentregistrationService = studentregistrationService;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public StatisticStrategy getStatistic() {
		return statistic;
	}

	public void setStatistic(StatisticStrategy statistic) {
		this.statistic = statistic;
	}
}