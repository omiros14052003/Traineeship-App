package myy803.traineeshipapp.services.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.mappers.StudentMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;
import myy803.traineeshipapp.mappers.UserMapper;
import myy803.traineeshipapp.services.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private TraineeshipPositionMapper traineeshipPositionMapper;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private StudentMapper studentMapper;
    
    @Mock
    private Model model;
    
    @InjectMocks
    private StudentServiceImpl studentService;
    private Student testStudent;
    private TraineeshipPosition testPosition;
    
    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId("student1");
    }

    @Test
    void testShowStudentProfileService() {
        when(studentMapper.getReferenceById("student1")).thenReturn(testStudent);
        studentService.showStudentProfileService("student1", model);
        verify(model).addAttribute("student", testStudent);
    }

    @Test
    void testSaveStudentProfileService_Success() {
        studentService.saveStudentProfileService(testStudent, "student1");
        verify(studentMapper).save(testStudent);
    }

    @Test
    void testSaveStudentProfileService_Failure() {
        doThrow(new RuntimeException("Save failed")).when(studentMapper).save(testStudent);
        try {
            studentService.saveStudentProfileService(testStudent, "student1");
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Save failed", e.getMessage());
        }
    }

    @Test
    void testSaveStudentAccountService() {
        User testUser = new User();
        testUser.setUsername("student2");
        Student newStudent = new Student();
        when(userMapper.findByUsername("student2")).thenReturn(testUser);
        studentService.saveStudentAccountService(newStudent, "student2");
        verify(studentMapper).save(newStudent);
    }

    @Test
    void testShowLogbookService() {
        List<TraineeshipPosition> positions = Arrays.asList(testPosition);
        when(traineeshipPositionMapper.findByStudent_Id("student1")).thenReturn(positions);
        studentService.showLogbookService("student1", model);
        verify(model).addAttribute("traineeships", positions);
    }

    @Test
    void testSaveLogbookService_Success() {
        testPosition = new TraineeshipPosition();
        testPosition.setId(1);
        testPosition.setLogbook("entry to be changed");
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        studentService.saveLogbookService(1, "updated logbook entry");
        assertEquals("updated logbook entry", testPosition.getLogbook());
        verify(traineeshipPositionMapper).save(testPosition); 
    }

    @Test
    void testSaveLogbookService_PositionNotFound() {
        when(traineeshipPositionMapper.getReferenceById(1))
                .thenThrow(new EntityNotFoundException("Position not found"));
        try {
            studentService.saveLogbookService(1, "logbook");
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Position not found", e.getMessage());
        }
        verify(traineeshipPositionMapper, never()).save(any());
    }


}