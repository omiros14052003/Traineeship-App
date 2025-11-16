package myy803.traineeshipapp.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.Mark;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;


public interface TraineeshipPositionMapper extends JpaRepository<TraineeshipPosition, Integer> {
	List<TraineeshipPosition> findByCompany_Id(String companyId);
	List<TraineeshipPosition> findByStudent_Id(String studentId);
	List<TraineeshipPosition> findByProfessor_Id(String professorId);
	List<TraineeshipPosition> findByStudentIsNull();
	List<TraineeshipPosition> findByStudentIsNotNullAndCompany_Id(String companyId);
	List<TraineeshipPosition> findByStudentIsNotNullAndProfessorIsNull();
	List<TraineeshipPosition> findByStudentIsNotNullAndProfessorIsNotNullAndMark(Mark mark);
}