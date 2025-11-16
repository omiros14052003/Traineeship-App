package myy803.traineeshipapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.EvaluationCompany;
import myy803.traineeshipapp.datamodel.EvaluationProfessor;
import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.matchingProfessorStrategies.MatchingProfessorFactory;
import myy803.traineeshipapp.datamodel.matchingProfessorStrategies.MatchingProfessorStrategy;
import myy803.traineeshipapp.mappers.EvaluationCompanyMapper;
import myy803.traineeshipapp.mappers.EvaluationProfessorMapper;
import myy803.traineeshipapp.mappers.ProfessorMapper;
import myy803.traineeshipapp.mappers.TraineeshipOfferMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;

@Service
public class CommitteeTraineeshipServiceImpl implements CommitteeTraineeshipService {
	
	@Autowired
	private ProfessorMapper professorMapper;
	
	@Autowired
	private TraineeshipOfferMapper traineeshipOfferMapper;
	
	@Autowired
	private TraineeshipPositionMapper traineeshipPositionMapper;
	
	@Autowired
	private EvaluationProfessorMapper evaluationProfessorMapper;
	
	@Autowired
	private EvaluationCompanyMapper evaluationCompanyMapper;
	
	@Autowired
	private MatchingProfessorFactory matchingProfessorFactory;

	@Override
	public void getTraineeshipsInProgressService(Model model) {
		model.addAttribute("traineeships_without_supervisor", traineeshipPositionMapper.findByStudentIsNotNullAndProfessorIsNull());
		model.addAttribute("traineeships_without_marks", traineeshipPositionMapper.findByStudentIsNotNullAndProfessorIsNotNullAndMark(myy803.traineeshipapp.datamodel.Mark.NOT_MARKED));
	}

	@Override
	public void showMatchingProfessorsService(int positionId, String strategy, Model model) {
		TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
		TraineeshipOffer offer = traineeshipOfferMapper.getReferenceByTraineeshipPosition(position);
		List<Professor> professors = professorMapper.findAll();
    	MatchingProfessorStrategy matchingProfessorStrategy = matchingProfessorFactory.create(strategy);
    	model.addAttribute("professors", matchingProfessorStrategy.findMatchingTraineeshipsForProfessor(offer, professors));
    	model.addAttribute("position_id", positionId);
	}

	@Override
	public boolean assignProfessorToPositionService(int positionId, String username) {
		TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
		if (position.getProfessor() == null) {
			Professor professor = professorMapper.getReferenceById(username);
			position.setProfessor(professor);
			professor.addSupervisee();
			professorMapper.save(professor);

			EvaluationProfessor evaluation = new EvaluationProfessor();
			evaluation.setTraineeshipPosition(position);
			evaluation.setEvaluator(professor);
			evaluationProfessorMapper.save(evaluation);
			position.setEvaluationProfessor(evaluation);
			traineeshipPositionMapper.save(position);
			return true;
		}
		return false;
	}

	@Override
	public void showTraineeshipForMarkingService(int positionId, Model model) {
		EvaluationCompany evaluationCompany = evaluationCompanyMapper.findByTraineeshipPosition_Id(positionId);
		EvaluationProfessor evaluationProfessor = evaluationProfessorMapper.findByTraineeshipPosition_Id(positionId);
		model.addAttribute("evaluation_company", evaluationCompany);
		model.addAttribute("evaluation_professor", evaluationProfessor);
    	model.addAttribute("position_id", positionId);
    	int avgGrade = (evaluationCompany.getAvgGradeOfStudent() + evaluationProfessor.getAvgGradeOfStudent())/2;
    	model.addAttribute("avgGrade", avgGrade);
	}

	@Override
	public void submitTraineeshipGradeService(String mark, int positionId) {
		TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
		if (mark.equals("passed")) {
			position.setPassed();
		}
		if (mark.equals("failed")) {
			position.setFailed();
		}
		Professor professor = position.getProfessor();
		professor.subtractSupervisee();
		traineeshipPositionMapper.save(position);
		professorMapper.save(professor);
	}
}