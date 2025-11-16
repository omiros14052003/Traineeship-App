package myy803.traineeshipapp.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.EvaluationProfessor;

public interface EvaluationProfessorMapper extends JpaRepository<EvaluationProfessor, Integer> {
	List<EvaluationProfessor> findByEvaluator_Id(String id);
	EvaluationProfessor findByTraineeshipPosition_Id(int id);
}