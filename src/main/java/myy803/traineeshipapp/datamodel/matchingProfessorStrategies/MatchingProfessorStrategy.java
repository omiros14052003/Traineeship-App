package myy803.traineeshipapp.datamodel.matchingProfessorStrategies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

public abstract class MatchingProfessorStrategy{
	
	public abstract List<Professor> findMatchingTraineeshipsForProfessor(TraineeshipOffer offer, List<Professor> professor);
	
	protected boolean checkIfInterestsAllignWithTopics(String interestsString, String topicsString) {
		if (interestsString == null || topicsString == null) {
	        return false;
	    }
	    
	    Set<String> interests = new HashSet<>(Arrays.asList(interestsString.trim().split("\\s*,\\s*")));
	    Set<String> topics = new HashSet<>(Arrays.asList(topicsString.trim().split("\\s*,\\s*")));
	    
	    if (interests.isEmpty() || topics.isEmpty()) {
	        return false;
	    }
	    
	    Set<String> intersection = new HashSet<>(interests);
	    intersection.retainAll(topics);

	    Set<String> union = new HashSet<>(interests);
	    union.addAll(topics);

	    double similarity = (double) intersection.size() / union.size();

	    return similarity >= 0.3;
	};
	
	protected boolean checkIfNumberThresholdIsFulfilled(int numberToCheck, int threshold) {
		return numberToCheck <= threshold;
	}
}