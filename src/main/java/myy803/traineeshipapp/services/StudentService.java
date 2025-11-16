package myy803.traineeshipapp.services;

import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.Student;

public interface StudentService {
	
    void showLogbookService(String username, Model model);
    
    void saveLogbookService(int positionId, String logbook);
    
    Student showStudentProfileService(String username, Model model);
    
    void saveStudentProfileService(Student student, String username);
    
    public void saveStudentAccountService(Student student, String username);
}