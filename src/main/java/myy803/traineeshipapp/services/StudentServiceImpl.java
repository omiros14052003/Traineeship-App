package myy803.traineeshipapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.mappers.StudentMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;
import myy803.traineeshipapp.mappers.UserMapper;

@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private TraineeshipPositionMapper traineeshipPositionMapper;
    
    @Autowired
	private UserMapper userMapper;
    
    @Autowired
	private StudentMapper studentMapper;

    @Override
    public Student showStudentProfileService(String username, Model model) {
        Student student = studentMapper.getReferenceById(username);
        model.addAttribute("student", student);
        return student;
    }

    @Override
    public void saveStudentProfileService(Student student, String username) {
        studentMapper.save(student);
    }
    
    @Override
    public void saveStudentAccountService(Student student, String username) {
        User user = userMapper.findByUsername(username);
        student.setId(user.getUsername());
        studentMapper.save(student);
    }
    
    @Override
    public void showLogbookService(String username, Model model) {
    	model.addAttribute("traineeships", traineeshipPositionMapper.findByStudent_Id(username));
    }
    
    @Override
    public void saveLogbookService(int positionId, String logbook) {
		TraineeshipPosition traineeship = traineeshipPositionMapper.getReferenceById(positionId);
        traineeship.setLogbook(logbook);
        traineeshipPositionMapper.save(traineeship);
    }
}