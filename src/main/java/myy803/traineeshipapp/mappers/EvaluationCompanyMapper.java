package myy803.traineeshipapp.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.EvaluationCompany;

public interface EvaluationCompanyMapper extends JpaRepository<EvaluationCompany, Integer> {
	List<EvaluationCompany> findByEvaluator_Id(String id);
	EvaluationCompany findByTraineeshipPosition_Id(int id);
}