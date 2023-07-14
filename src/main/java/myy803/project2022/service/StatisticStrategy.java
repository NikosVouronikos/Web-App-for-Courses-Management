package myy803.project2022.service;
import java.util.List;

import org.springframework.stereotype.Service;

import myy803.project2022.model.StudentRegistration;

@Service
public interface StatisticStrategy {
	
	public double Median(List<StudentRegistration> theSRS,int mode);
	
	public double MaxMin(List<StudentRegistration> theSRS,int mod,int mode);
	
	public double Mean(List<StudentRegistration> theSRS,int mode);
	
	public double StandardDeviation(List<StudentRegistration> theSRS,int mode);
	
	public double Variance(List<StudentRegistration> theSRS,int mode);
	
	public double SPercentage(List<StudentRegistration> theSRS,int mode);
	
	public double Skewness(List<StudentRegistration> theSRS,int mode);
	
	public double Kurtosis(List<StudentRegistration> theSRS,int mode);
}
