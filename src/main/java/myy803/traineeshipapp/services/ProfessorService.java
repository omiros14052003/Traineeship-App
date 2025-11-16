package myy803.traineeshipapp.services;

import myy803.traineeshipapp.datamodel.EvaluationProfessor;
import myy803.traineeshipapp.datamodel.Professor;

import java.security.Principal;

import org.springframework.ui.Model;

public interface ProfessorService {
	
    public void getSupervisedTraineeshipsService(Principal principal, Model model);
    
    public void saveProfessorAccountService(Professor professor, String username);
    
    public void getEvaluationService(Principal principal, int positionId, Model model);
    
    public void saveEvaluationService(EvaluationProfessor evaluation, int traineeshipId, Principal principal);
    
    void saveProfessorProfileService(Professor professor, String username);
    
    Professor showProfessorProfileService(String username, Model model);
}
