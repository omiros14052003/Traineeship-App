package myy803.traineeshipapp.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.Student;


public interface StudentMapper extends JpaRepository<Student, String> {
	Student getReferenceById(String id);
	List<Student> getByLookingForPosition(Boolean interested);
}