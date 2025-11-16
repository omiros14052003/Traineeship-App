package myy803.traineeshipapp.services;

import org.springframework.ui.Model;

public interface CommitteeTraineeshipService {
    
    public boolean assignProfessorToPositionService(int positionId, String username);

	public void getTraineeshipsInProgressService(Model model);

	public void showMatchingProfessorsService(int positionId, String strategy, Model model);
	
	public void showTraineeshipForMarkingService(int positionId, Model model);
	
	public void submitTraineeshipGradeService(String mark, int positionId);
}
