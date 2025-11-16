package myy803.traineeshipapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeshipapp.datamodel.EvaluationProfessor;
import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.services.ProfessorService;

import java.security.Principal;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/dashboard")
    public String professorDashboard() {
        return "professor/dashboard";
    }

    @GetMapping("/supervised_positions")
    public String getSupervisedTraineeships(Model model, Principal principal) {
    	professorService.getSupervisedTraineeshipsService(principal, model);
        return "professor/supervised_positions";
    }


    @GetMapping("/supervised_positions/evaluate")
    public String showEvaluationForm(@RequestParam("traineeshipId") int traineeshipId, Model model, Principal principal) {
    	professorService.getEvaluationService(principal, traineeshipId, model);
        return "professor/professor_evaluation";
    }

    @PostMapping("/supervised_positions/evaluate/save")
    public String saveEvaluation(@ModelAttribute("evaluation") EvaluationProfessor evaluation, @RequestParam("traineeshipId") int traineeshipId, Principal principal) {
        professorService.saveEvaluationService(evaluation, traineeshipId, principal);
        return "redirect:/professor/supervised_positions";
    }
    
    @RequestMapping("/profile")
    public String showProfessorProfile(Model model, Principal principal) {
    	professorService.showProfessorProfileService(principal.getName(), model);
        return "professor/profile";
    }
    
    @PostMapping("/save_profile")
    public String saveProfessorProfile(@ModelAttribute("professor") Professor professor, Principal principal) {
        professorService.saveProfessorProfileService(professor, principal.getName());
        return "redirect:/professor/dashboard";
    }
}
