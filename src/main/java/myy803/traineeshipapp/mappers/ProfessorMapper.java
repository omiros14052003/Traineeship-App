package myy803.traineeshipapp.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.Professor;


public interface ProfessorMapper extends JpaRepository<Professor, String> {
	Professor getReferenceById(String id);
}