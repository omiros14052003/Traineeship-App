package myy803.traineeshipapp.datamodel.matchingProfessorStrategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

@Component
public class MatchingProfessorBySuperviseeNumberStrategy extends MatchingProfessorStrategy {
	
	static final int MAXIMUM_SUPERVISEES = 9;

	@Override
	public List<Professor> findMatchingTraineeshipsForProfessor(TraineeshipOffer offer, List<Professor> allProfessors) {
		List<Professor> matchingProfessors = new ArrayList<Professor>();
		
		for (Professor professor : allProfessors) {
    		if(professor.getSuperviseeNum() < MAXIMUM_SUPERVISEES) {
    			matchingProfessors.add(professor);
    		}
		}
		matchingProfessors.sort(Comparator.comparing(Professor::getSuperviseeNum));
		
		return matchingProfessors;
	}
}