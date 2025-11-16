package myy803.traineeshipapp.datamodel.matchingStudentStrategies;

import org.springframework.stereotype.Component;

@Component
public class MatchingStudentFactory {
	
	public MatchingStudentStrategy create(String strategy) {
		return switch (strategy) {
        case "Location" -> new MatchingStudentByLocationStrategy();
        case "Interests" -> new MatchingStudentByInterestsStrategy();
        case "Location and Interests" -> new MatchingStudentByLocationAndInterestsStrategy();
		default -> throw new IllegalArgumentException("Unexpected value: " + strategy);
		};
	}
}