package myy803.project2022.service;

import java.util.List;

import org.springframework.stereotype.Service;

import myy803.project2022.model.Course;

@Service
public interface CourseService {
	
	public List<Course> findAll();
	
	public Course findById(int id);
	
	public Course findByName(String coursename);
	
	public void save(Course theCourse);
	
	public void deleteById(int id);
	
	public void deleteAll();
}
