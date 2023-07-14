package myy803.project2022.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.project2022.model.Course;

@Repository
public interface CourseDAO extends JpaRepository<Course, Integer> {
	
	public Course findById(int id);
}
