import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import myy803.project2022.CoursesManagmentApplication;
import myy803.project2022.model.StudentRegistration;
import myy803.project2022.service.StatisticStrategy;

@SpringBootTest(classes = CoursesManagmentApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class TestStatisticStrategy {
	
	@Autowired
	StatisticStrategy stats;
	
	@Test
	public void testStatisticStrategyIsNotNull() {
		Assertions.assertNotNull(stats);
	}
	
	@Test
	public void testMaxMinMethodReturnsMax() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","7","5","5.8"));
		double max = stats.MaxMin(students, 0, 2);
		Assertions.assertEquals(6.8, max);
	}
	
	@Test
	public void testMaxMinMethodReturnsMin() {
		/*Will check for project grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","7","5","5.8"));
		double min = stats.MaxMin(students, 1, 0);
		Assertions.assertEquals(7, min);
	}
	
	@Test
	public void testMeanMethodReturnsMean() {
		/*Will check for exam grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","7","5","5.8"));
		double mean = stats.Mean(students, 1);
		Assertions.assertEquals(5.33, mean);
	}
	
	@Test
	public void testMedianMethodReturnsMedian() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","7","5","5.8"));
		double median = stats.Median(students, 2);
		Assertions.assertEquals(6.2, median);
	}
	
	@Test
	public void testStandardDeviationMethod() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","7","5","5.8"));
		double sdev = stats.StandardDeviation(students, 2);
        DecimalFormat df = new DecimalFormat("#.###");
        String s = df.format(sdev);
        Assertions.assertEquals(0.503,Double.parseDouble(s));
	}
	
	@Test
	public void testVarianceMethod() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","7","5","5.8"));
		double var = stats.Variance(students, 2);
        DecimalFormat df = new DecimalFormat("#.###");
        String s = df.format(var);
        Assertions.assertEquals(0.253,Double.parseDouble(s));
	}
	
	@Test
	public void testSuccessPercentageMethod() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","3","","3"));
		double sper = stats.SPercentage(students, 2);
        DecimalFormat df = new DecimalFormat("#.###");
        String s = df.format(sper);
        Assertions.assertEquals(66.667,Double.parseDouble(s));
	}
	
	@Test
	public void testSkewnessMethod() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","3","","3"));
		double skr = stats.Skewness(students, 2);
        DecimalFormat df = new DecimalFormat("#.###");
        String s = df.format(skr);
        Assertions.assertEquals(-1.565,Double.parseDouble(s));
	}
	
	@Test
	public void testKurtosisMethod() {
		/*Will check for final grades*/
		List<StudentRegistration> students = new ArrayList<StudentRegistration>();
		students.add(new StudentRegistration(1,"2151","Ilias Tsinoremas","2022","8","8","6","6.8"));
		students.add(new StudentRegistration(1,"4327","Nikolaos Vouronikos","2022","8","8","5","6.2"));
		students.add(new StudentRegistration(1,"4292","Eirini Thoma","2022","8","3","","3"));
		students.add(new StudentRegistration(1,"0000","Default","2022","8","5","5","5"));
		double kur = stats.Kurtosis(students, 2);
        DecimalFormat df = new DecimalFormat("#.###");
        String s = df.format(kur);
        Assertions.assertEquals(0.101,Double.parseDouble(s));
	}
}
