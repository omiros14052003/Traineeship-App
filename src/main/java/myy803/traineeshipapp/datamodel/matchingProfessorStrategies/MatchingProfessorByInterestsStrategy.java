package myy803.traineeshipapp.datamodel.matchingProfessorStrategies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

@Component
public class MatchingProfessorByInterestsStrategy extends MatchingProfessorStrategy {

	@Override
	public List<Professor> findMatchingTraineeshipsForProfessor(TraineeshipOffer offer, List<Professor> allProfessors) {
		String offerTopics = offer.getTopics(); 
		List<Professor> matchingProfessors = new ArrayList<Professor>();
		
		for (Professor professor : allProfessors) {
    		if(checkIfInterestsAllignWithTopics(professor.getInterests(), offerTopics)) {
    			matchingProfessors.add(professor);
    		}
		}
		
		return matchingProfessors;
	}
}