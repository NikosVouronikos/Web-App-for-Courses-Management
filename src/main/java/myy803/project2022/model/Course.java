package myy803.project2022.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Course")
/*Class Course mirrors Table Course in our mysql script*/
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "coursename")
	private String coursename;
	
	@Column(name = "semester")
	private String semester;
	
	@Column(name = "period")
	private String period;
	
	@Column(name = "ects")
	private String ects;
	
	@Column(name = "ctype")
	private String ctype;
	
	@Column(name = "projectper")
	private String projectper;
	
	@Column(name = "examper")
	private String examper;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
	private List<StudentRegistration> studentregistrations = new ArrayList<StudentRegistration>();
	
	private double maximum;
	
	private double minimum;
	
	private double meangrade;
	
	private double mediangrade;
	
	private double sdeviation;
	
	private double variance;
	
	private double sper;
	
	private double skewness;
	
	private double percentiles;
	
	private double kurtosis;
	
	public Course() {
		super();
	}
	
	public Course(int id, String coursename, String semester
					, String period, String ects, String ctype, 
					String projectper, String examper) {
		super();
		this.id = id;
		this.coursename = coursename;
		this.semester = semester;
		this.period = period;
		this.ects = ects;
		this.ctype = ctype;
		this.projectper = projectper;
		this.examper = examper;
	}

	public void addStudentRegistrations(StudentRegistration theSR){
		   studentregistrations.add(theSR);
		   theSR.setCourse(this);
		}

	public void removeStudentRegistrations(StudentRegistration theSR){
		studentregistrations.remove(theSR);
		theSR.setCourse(null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getEcts() {
		return ects;
	}

	public void setEcts(String ects) {
		this.ects = ects;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	
	public String getProjectper() {
		return projectper;
	}

	public void setProjectper(String projectper) {
		this.projectper = projectper;
	}

	public String getExamper() {
		return examper;
	}

	public void setExamper(String examper) {
		this.examper = examper;
	}
	
	public List<StudentRegistration> getStudentRegistrations() {
		return studentregistrations;
	}

	public void setStudentRegistrations(List<StudentRegistration> studentRegistrations) {
		studentregistrations = studentRegistrations;
	}
	
	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
	}
	
	public double getMinimum() {
		return minimum;
	}

	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}
	
	public double getMeangrade() {
		return meangrade;
	}

	public void setMeangrade(double meangrade) {
		this.meangrade = meangrade;
	}
	
	public double getMediangrade() {
		return mediangrade;
	}

	public void setMediangrade(double mediangrade) {
		this.mediangrade = mediangrade;
	}
	
	public double getSdeviation() {
		return sdeviation;
	}

	public void setSdeviation(double sdeviation) {
		this.sdeviation = sdeviation;
	}
	
	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}
	
	public double getSper() {
		return sper;
	}

	public void setSper(double sper) {
		this.sper = sper;
	}
	
	public double getSkewness() {
		return skewness;
	}

	public void setSkewness(double skewness) {
		this.skewness = skewness;
	}
	
	public double getPercentiles() {
		return percentiles;
	}

	public void setPercentiles(double percentiles) {
		this.percentiles = percentiles;
	}
	
	public double getKurtosis() {
		return kurtosis;
	}

	public void setKurtosis(double kurtosis) {
		this.kurtosis = kurtosis;
	}
	
	@Override
	public String toString() {
		return "CourseObj -> course_code = " + id + ", course _name = "
				+ coursename + ", semester = " + semester + ", period = "
				+ period + ", ects = " + ects + ", ctype = " + ctype 
				+ ", projectper = " + projectper + ", examper = " + examper;
	}	
}