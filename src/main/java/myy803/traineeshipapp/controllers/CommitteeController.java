package myy803.traineeshipapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import myy803.traineeshipapp.services.CommitteeStudentService;
import myy803.traineeshipapp.services.CommitteeTraineeshipService;


@Controller
@RequestMapping("/committee")
public class CommitteeController {
	
	@Autowired
    private CommitteeStudentService committeeStudentService;
	
	@Autowired
    private CommitteeTraineeshipService committeeTraineeshipService;

	@GetMapping("/dashboard")
    public String committeeDashboard() {
        return "committee/dashboard";
    }
	
	@GetMapping("/interested_students_list")
    public String getInterestedStudents(Model model) {
		committeeStudentService.getInterestedStudentsService(model);
		
        return "committee/interested_students_list";
    }
	
	@GetMapping("/show_matching_offers")
    public String showMatchingOffers(@RequestParam("student_id") String id,@RequestParam("strategy") String strategy, Model model) {
		committeeStudentService.showMatchingOffersService(id, strategy, model);
        return "committee/show_matching_offers";
    }
	
	@PostMapping("/assign_student_to_position")
	public String assignStudentToPosition(@RequestParam("student_id") String username, @RequestParam("position_id") int id, RedirectAttributes redirectAttributes, Model model) {
		committeeStudentService.assignStudentToPositionService(username, id);
		return "redirect:/committee/interested_students_list";
    }
	
	@GetMapping("/traineeships_in_progress")
    public String getTraineeshipsInProgress(Model model) {
		committeeTraineeshipService.getTraineeshipsInProgressService(model);
        return "committee/traineeships_in_progress";
    }
	
	@GetMapping("/show_matching_professors")
    public String showMatchingProfessors(@RequestParam("position_id") int positionId, @RequestParam("strategy") String strategy, Model model) {
		committeeTraineeshipService.showMatchingProfessorsService(positionId, strategy, model);
        return "committee/show_matching_professors";
    }
	
	@PostMapping("/assign_professor_to_traineeship_position")
	public String assignProfessorToPosition(@RequestParam("professor_id") String username, @RequestParam("position_id") int positionId, RedirectAttributes redirectAttributes) {
		committeeTraineeshipService.assignProfessorToPositionService(positionId, username);
		return "redirect:/committee/traineeships_in_progress";
    }
	
	@GetMapping("/show_traineeship_for_marking")
    public String showTraineeshipForMarking(@RequestParam("position_id") int positionId, Model model) {
		committeeTraineeshipService.showTraineeshipForMarkingService(positionId, model);
        return "committee/show_traineeship_for_marking";
    }
	
	@PostMapping("/submit_traineeship_grade")
    public String submitTraineeshipGrade(@RequestParam("mark") String mark, @RequestParam("position_id") int positionId, Model model) {
		committeeTraineeshipService.submitTraineeshipGradeService(mark, positionId);
		return "redirect:/committee/traineeships_in_progress";
    }
}