package myy803.traineeshipapp.services;

import org.springframework.ui.Model;

public interface CommitteeStudentService {
	
    public void getInterestedStudentsService(Model model);
    
    public boolean assignStudentToPositionService(String username, int positionId);

	public void showMatchingOffersService(String username, String strategy, Model model);
}
