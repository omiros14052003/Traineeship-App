package myy803.traineeshipapp.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/dashboard")
    public String studentDashboard() {
        return "student/dashboard";
    }
    
    @RequestMapping("/profile")
    public String showStudentProfile(Model model, Principal principal) {
    	studentService.showStudentProfileService(principal.getName(), model);
        return "student/profile";
    }
    
    @PostMapping("/save_profile")
    public String saveStudentProfile(@ModelAttribute("student") Student student, Principal principal) {
        studentService.saveStudentProfileService(student, principal.getName());
        return "redirect:/student/dashboard";
    }

    @GetMapping("/logbook")
    public String showLogbook(Model model, Principal principal) {
        studentService.showLogbookService(principal.getName(), model);
        return "student/logbook";
    }

    @PostMapping("/save_logbook")
    public String saveLogbook(@ModelAttribute("id") int positionId, String logbook) {
        studentService.saveLogbookService(positionId, logbook);
        return "redirect:/student/logbook";
    }
}