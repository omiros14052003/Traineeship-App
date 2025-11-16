package myy803.traineeshipapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.EvaluationProfessor;
import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.mappers.EvaluationProfessorMapper;
import myy803.traineeshipapp.mappers.ProfessorMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;
import myy803.traineeshipapp.mappers.UserMapper;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private TraineeshipPositionMapper traineeshipPositionMapper;
    
    @Autowired
    private ProfessorMapper professorMapper;
    
    @Autowired
    private EvaluationProfessorMapper evaluationProfessorMapper;
    
    @Autowired
	private UserMapper userMapper;

    @Override
    public Professor showProfessorProfileService(String username, Model model) {
        Professor professor = professorMapper.getReferenceById(username);
        model.addAttribute("professor", professor);
        return professor;
    }

    @Override
    public void saveProfessorAccountService(Professor professor, String username) {
        User user = userMapper.findByUsername(username);
        professor.setId(user.getUsername());
        professorMapper.save(professor);
    }
    
    @Override
    public void saveProfessorProfileService(Professor professor, String username) {
    	Professor professorInDB = professorMapper.getReferenceById(username);
    	professorInDB.setName(professor.getName());
    	professorInDB.setInterests(professor.getInterests());
        professorMapper.save(professorInDB);
    }
    
    @Override
    public void getSupervisedTraineeshipsService(Principal principal, Model model) {
    	String professorId = principal.getName();
        model.addAttribute("supervisedTraineeships", traineeshipPositionMapper.findByProfessor_Id(professorId));
    }

	@Override
	public void getEvaluationService(Principal principal, int positionId, Model model) {
		EvaluationProfessor evaluation = evaluationProfessorMapper.findByTraineeshipPosition_Id(positionId);
        TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
        List<Integer> marks = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
        model.addAttribute("marks", marks);
        model.addAttribute("grade", position.getMark());
        model.addAttribute("evaluation", evaluation);
	}

	@Override
	public void saveEvaluationService(EvaluationProfessor evaluation, int traineeshipId, Principal principal) {
		Professor evaluator = professorMapper.getReferenceById(principal.getName());
        EvaluationProfessor evaluationInDB = evaluationProfessorMapper.findByTraineeshipPosition_Id(traineeshipId);
        if (evaluator.getId().equals(principal.getName())){
        	evaluationInDB.setEffectivenessGrade(evaluation.getEffectivenessGrade());
        	evaluationInDB.setEfficiencyGrade(evaluation.getEfficiencyGrade());
        	evaluationInDB.setMotivationGrade(evaluation.getMotivationGrade());
        	evaluationInDB.setFacilitiesGrade(evaluation.getFacilitiesGrade());
        	evaluationInDB.setGuidanceGrade(evaluation.getGuidanceGrade());
        }
        evaluationProfessorMapper.save(evaluationInDB);
	}
}
