package myy803.project2022.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "StudentRegistration")
/*Class StudentRegistration mirrors Table StudentRegistration in our mysql script*/
public class StudentRegistration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "am")
	private String am;
	
	@Column(name = "studentname")
	private String studentname;
	
	@Column(name = "yearofreg")
	private String yearofreg;
	
	@Column(name = "currsemester")
	private String currsemester;
	
	@Column(name = "projectgrade")
	private String projectgrade;
	
	@Column(name = "examgrade")
	private String examgrade;
	
	@Column(name = "finalgrade")
	private String finalgrade;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "courseid", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Course course;
	
	private int id2;

	public StudentRegistration() {
		super();
	}
	
	public StudentRegistration(int id, String am,
								String studentname, String yearofreg,
								String currsemester,String projectgrade,
								String examgrade,String finalgrade) {
		super();
		this.id = id;
		this.am = am;
		this.studentname = studentname;
		this.yearofreg = yearofreg;
		this.currsemester = currsemester;
		this.projectgrade = projectgrade;
		this.examgrade = examgrade;
		this.finalgrade = finalgrade;
	}
	
	public StudentRegistration(int id, String am,
								String studentname,
								String yearofreg, String currsemester,
								String projectgrade, String examgrade,
								String finalgrade, Course course,
								int id2) {
		super();
		this.id = id;
		this.am = am;
		this.studentname = studentname;
		this.yearofreg = yearofreg;
		this.currsemester = currsemester;
		this.projectgrade = projectgrade;
		this.examgrade = examgrade;
		this.finalgrade = finalgrade;
		this.course = course;
		this.id2 = id2;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}
	
	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getYearofreg() {
		return yearofreg;
	}

	public void setYearofreg(String yearofreg) {
		this.yearofreg = yearofreg;
	}

	public String getCurrsemester() {
		return currsemester;
	}

	public void setCurrsemester(String currsemester) {
		this.currsemester = currsemester;
	}

	public String getProjectgrade() {
		return projectgrade;
	}

	public void setProjectgrade(String projectgrade) {
		this.projectgrade = projectgrade;
	}
	
	public String getExamgrade() {
		return examgrade;
	}

	public void setExamgrade(String examgrade) {
		this.examgrade = examgrade;
	}
	
	public String getFinalgrade() {
		return finalgrade;
	}

	public void setFinalgrade(String finalgrade) {
		this.finalgrade = finalgrade;
	}
	
	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}
	
	@Override
	public String toString() {
		return "StudentRegistrationObj -> student_code = " + id
				+ "am = " + am + ", student_name = " 
				+ studentname + ", year_of_reg = "+ yearofreg 
				+ ", curr_semester = " + currsemester
				+ ", projectgrade = " + projectgrade
				+ ", examgrade = " + examgrade
				+ ", finalgrade = " + finalgrade;
	}
}