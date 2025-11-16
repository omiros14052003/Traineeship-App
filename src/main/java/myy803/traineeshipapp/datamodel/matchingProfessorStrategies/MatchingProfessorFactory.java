package myy803.traineeshipapp.datamodel.matchingProfessorStrategies;

import org.springframework.stereotype.Component;

@Component
public class MatchingProfessorFactory {
	
	public MatchingProfessorStrategy create(String strategy) {
		return switch (strategy) {
        case "Interests" -> new MatchingProfessorByInterestsStrategy();
        case "Supervisee Number" -> new MatchingProfessorBySuperviseeNumberStrategy();
		default -> throw new IllegalArgumentException("Unexpected value: " + strategy);
		};
	}
}