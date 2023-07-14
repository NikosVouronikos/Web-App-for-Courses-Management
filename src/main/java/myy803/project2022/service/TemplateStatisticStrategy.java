package myy803.project2022.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Component;

import myy803.project2022.model.StudentRegistration;

@Component
public class TemplateStatisticStrategy implements StatisticStrategy {
	
	/*mod = 0 -> calculate max
	 *mod = 1 -> calculate min
	 */
	public double MaxMin(List<StudentRegistration> theSRS,int mod,int mode) {
		List<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
		if(mod == 0) {
			double max = Collections.max(grades);
			return max;
		}
		else {
			double min = Collections.min(grades);
			return min;
		}
	}
	
	/*Mean value calculation*/
	public double Mean(List<StudentRegistration> theSRS,int mode) {
		double sum = 0;
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				sum = sum + grade;
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				sum = sum + grade;
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				sum = sum + grade;
			}
		}
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double mean = sum/(theSRS.size());
		mean = Double.valueOf(twoDForm.format(mean));
		return mean;
	}
	
	/*Median calculation*/
	public double Median(List<StudentRegistration> theSRS,int mode) {
		
		ArrayList<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
        Collections.sort(grades);

        if (grades.size() % 2 == 1)
            return grades.get((grades.size() + 1) / 2 - 1);
        else {
            double lower = grades.get(grades.size() / 2 - 1);
            double upper = grades.get(grades.size() / 2);

            return (lower + upper) / 2.0;
        }
    }
	
	public double StandardDeviation(List<StudentRegistration> theSRS,int mode) {
		
		ArrayList<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		
		for(double v : grades) {
			descriptiveStatistics.addValue(v);
		}
		
		double sdev = descriptiveStatistics.getStandardDeviation();
		return sdev;
	}
	
	public double Variance(List<StudentRegistration> theSRS,int mode) {
		
		ArrayList<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		
		for(double v : grades) {
			descriptiveStatistics.addValue(v);
		}
		
		double var = descriptiveStatistics.getVariance();
		return var;
	}
	
	public double SPercentage(List<StudentRegistration> theSRS,int mode) {
		
		ArrayList<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
		double count = 0;
		double length = grades.size();
		for(double v : grades) {
			if(v >= 5) {
				count = count + 1;
			}
		}
		
		double per = count/length;
		double p = per*100;
		return p;
	}
	
	public double Skewness(List<StudentRegistration> theSRS,int mode) {
		
		ArrayList<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		
		for(double v : grades) {
			descriptiveStatistics.addValue(v);
		}
		
		double sk = descriptiveStatistics.getSkewness();
		return sk;
	}
	
	public double Kurtosis(List<StudentRegistration> theSRS,int mode) {
		
		ArrayList<Double> grades = new ArrayList<Double>();
		
		if(mode == 0) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getProjectgrade());
				grades.add(grade);
			}
		}
		else if(mode == 1) {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getExamgrade());
				grades.add(grade);
			}
		}
		else {
			for(int i = 0; i < theSRS.size(); i++) {
				double grade = Double.parseDouble(theSRS.get(i).getFinalgrade());
				grades.add(grade);
			}
		}
		
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		
		for(double v : grades) {
			descriptiveStatistics.addValue(v);
		}
		
		double kur = descriptiveStatistics.getKurtosis();
		return kur;
	}
}
