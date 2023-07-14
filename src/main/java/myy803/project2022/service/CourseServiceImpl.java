package myy803.project2022.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import myy803.project2022.dao.CourseDAO;
import myy803.project2022.model.Course;

@Component
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseDAO courseRepository;
	
	public CourseServiceImpl() {
		super();
	}
	
	@Autowired
	public CourseServiceImpl(CourseDAO theCourseDAO) {
		courseRepository = theCourseDAO;
	}

	@Override
	@Transactional
	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	@Override
	@Transactional
	public Course findById(int id) {
		
		Course obj = courseRepository.findById(id);
		if(obj != null) {
			return obj;
		}
		else {
			throw new RuntimeException("Dit not find course code -> " + id);
		}
	}
	
	@Override
	@Transactional
	public Course findByName(String coursename) {
		
		List<Course> courses = courseRepository.findAll();
		for(Course obj : courses) {
			if(obj.getCoursename().equals(coursename)) {
				return obj;
			}
		}
		throw new RuntimeException("Dit not find course with name -> " + coursename);
	}

	@Override
	@Transactional
	public void save(Course theCourse) {
		courseRepository.save(theCourse);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		courseRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public void deleteAll() {
		courseRepository.deleteAll();
	}
	
	public CourseDAO getCourseRepository() {
		return courseRepository;
	}

	public void setCourseRepository(CourseDAO courseRepository) {
		this.courseRepository = courseRepository;
	}

}
