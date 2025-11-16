package myy803.traineeshipapp.datamodel.matchingStudentStrategies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

@Component
public class MatchingStudentByInterestsStrategy extends MatchingStudentStrategy {
	@Override
	public List<TraineeshipOffer> findMatchingTraineeshipsForStudent(Student student, List<TraineeshipOffer> allOffers) {
		String studentSkills = student.getSkills();
		String studentInterests = student.getInterests();
		List<TraineeshipOffer> matchingOffers = new ArrayList<TraineeshipOffer>();
		
		for (TraineeshipOffer offer : allOffers) {
    		if(checkIfParticipantHasRequiredSkills(studentSkills, offer.getSkills()) && checkIfInterestsAllignWithTopics(studentInterests, offer.getTopics())) {
    			matchingOffers.add(offer);
    		}
		}
		
		return matchingOffers;
	}
}