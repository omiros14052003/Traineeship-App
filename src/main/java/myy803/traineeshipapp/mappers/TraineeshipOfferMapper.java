package myy803.traineeshipapp.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.TraineeshipOffer;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;


public interface TraineeshipOfferMapper extends JpaRepository<TraineeshipOffer, Integer> {
	List<TraineeshipOffer> findByCompany_Id(String companyId);
	TraineeshipOffer getReferenceByTraineeshipPosition(TraineeshipPosition position);
	List<TraineeshipOffer> findByTraineeshipPositionStudentIsNull();
}