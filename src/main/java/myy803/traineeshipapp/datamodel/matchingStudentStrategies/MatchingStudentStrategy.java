package myy803.traineeshipapp.datamodel.matchingStudentStrategies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

public abstract class MatchingStudentStrategy{
	
	public abstract List<TraineeshipOffer> findMatchingTraineeshipsForStudent(Student student, List<TraineeshipOffer> allOffers);
	
	protected boolean checkIfParticipantHasRequiredSkills(String participantSkillsString, String offerSkillsString) {
		if (offerSkillsString.equals("")) {
			return true;
		}
		
		List<String> participantSkills = Arrays.asList(participantSkillsString.trim().split("\\s*,\\s*"));
		String[] offerSkills = offerSkillsString.trim().split("\\s*,\\s*");
		for (String requiredSkill : offerSkills) {
			if(!participantSkills.contains(requiredSkill)) {
				return false;
			}
		}
		return true;
	};
	
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
	
	protected boolean checkIfLocationsMatch(String participantLocation, String offerLocation) {
		return participantLocation.equals(offerLocation);
	};
}