package myy803.traineeshipapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import myy803.traineeshipapp.mappers.EvaluationCompanyMapper;
import myy803.traineeshipapp.mappers.StudentMapper;
import myy803.traineeshipapp.mappers.TraineeshipOfferMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.matchingStudentStrategies.MatchingStudentFactory;
import myy803.traineeshipapp.datamodel.matchingStudentStrategies.MatchingStudentStrategy;
import myy803.traineeshipapp.datamodel.EvaluationCompany;
import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

@Service
public class CommitteeStudentServiceImpl implements CommitteeStudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
	@Autowired
	private TraineeshipOfferMapper traineeshipOfferMapper;
    
    @Autowired
    private TraineeshipPositionMapper traineeshipPositionMapper;
    
    @Autowired
    private EvaluationCompanyMapper evaluationCompanyMapper;
    
    @Autowired
    private MatchingStudentFactory matchingStudentFactory;
    
    @Override
	public void getInterestedStudentsService(Model model) {
		model.addAttribute("students", studentMapper.getByLookingForPosition(true));
	}

    @Override
    public void showMatchingOffersService(String username, String strategy, Model model) {
    	List<TraineeshipOffer> allOffers = traineeshipOfferMapper.findByTraineeshipPositionStudentIsNull();
		Student student = studentMapper.getReferenceById(username);
    	MatchingStudentStrategy matchingStudentStrategy = matchingStudentFactory.create(strategy);
    	model.addAttribute("offers", matchingStudentStrategy.findMatchingTraineeshipsForStudent(student, allOffers));
    	model.addAttribute("student_id", username);
    }

	@Override
	public boolean assignStudentToPositionService(String username, int positionId) {
		TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
		if (position.getStudent() == null) {
			Student student = studentMapper.getReferenceById(username);
			position.setStudent(student);
			student.setLookingForPosition(false);
			
			
			EvaluationCompany evaluation = new EvaluationCompany();
	        evaluation.setTraineeshipPosition(position);
	        evaluation.setEvaluator(position.getCompany());
	        evaluationCompanyMapper.save(evaluation);
	        position.setEvaluationCompany(evaluation);
	        traineeshipPositionMapper.save(position);
			return true;
		}
		return false;
	}
}
