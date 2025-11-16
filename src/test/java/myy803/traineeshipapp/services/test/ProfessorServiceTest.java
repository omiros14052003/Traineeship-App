package myy803.traineeshipapp.services.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.EvaluationProfessor;
import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.mappers.EvaluationProfessorMapper;
import myy803.traineeshipapp.mappers.ProfessorMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;
import myy803.traineeshipapp.mappers.UserMapper;
import myy803.traineeshipapp.services.ProfessorServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

    @Mock
    private TraineeshipPositionMapper traineeshipPositionMapper;
    
    @Mock
    private ProfessorMapper professorMapper;
    
    @Mock
    private EvaluationProfessorMapper evaluationProfessorMapper;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private Model model;
    
    @Mock
    private Principal principal;
    
    @InjectMocks
    private ProfessorServiceImpl professorService;
    private Professor testProfessor;
    private EvaluationProfessor testEvaluation;

    @BeforeEach
    void setUp() {
        testProfessor = new Professor();
        testProfessor.setId("prof1");
    }

    @Test
    void testShowProfessorProfileService() {
        when(professorMapper.getReferenceById("prof1")).thenReturn(testProfessor);
        professorService.showProfessorProfileService("prof1", model);
        verify(model).addAttribute("professor", testProfessor);
    }

    @Test
    void testSaveProfessorProfileService_Success() {
        when(professorMapper.getReferenceById("prof1")).thenReturn(testProfessor);
        professorService.saveProfessorProfileService(testProfessor, "prof1");
        verify(professorMapper).save(testProfessor);
    }

    @Test
    void testSaveProfessorProfileService_Failure() {
        when(professorMapper.getReferenceById("prof1")).thenReturn(testProfessor);
        doThrow(new RuntimeException("Save failed")).when(professorMapper).save(testProfessor);
        try {
            professorService.saveProfessorProfileService(testProfessor, "prof1");
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Save failed", e.getMessage());
        }
    }
    
    @Test
    void testSaveProfessorAccountService() {
        User testUser = new User();
        testUser.setUsername("prof2");
        Professor newProfessor = new Professor();
        when(userMapper.findByUsername("prof2")).thenReturn(testUser);
        professorService.saveProfessorAccountService(newProfessor, "prof2");
        assertEquals("prof2", newProfessor.getId()); 
        verify(professorMapper).save(newProfessor);
    }

    @Test
    void testGetEvaluationService_EvaluationExists() {
        int positionId = 1;
        testEvaluation = new EvaluationProfessor();
        TraineeshipPosition mockPosition = new TraineeshipPosition();
        when(evaluationProfessorMapper.findByTraineeshipPosition_Id(positionId)).thenReturn(testEvaluation);
        when(traineeshipPositionMapper.getReferenceById(positionId)).thenReturn(mockPosition);
        professorService.getEvaluationService(principal, positionId, model);
        verify(evaluationProfessorMapper).findByTraineeshipPosition_Id(positionId);
        verify(traineeshipPositionMapper).getReferenceById(positionId);
        List<Integer> expectedMarks = List.of(1, 2, 3, 4, 5);
        verify(model).addAttribute("marks", expectedMarks);
        verify(model).addAttribute("grade", mockPosition.getMark());
        verify(model).addAttribute("evaluation", testEvaluation);
    }

    @Test
    void testGetEvaluationService_EvaluationDoesNotExist() {
        int positionId = 1;
        when(evaluationProfessorMapper.findByTraineeshipPosition_Id(positionId))
                .thenThrow(new EntityNotFoundException("Evaluation not found"));
        try {
            professorService.getEvaluationService(principal, positionId, model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Evaluation not found", e.getMessage());
        }
        verify(model, never()).addAttribute(eq("marks"), any());
        verify(model, never()).addAttribute(eq("evaluation"), any());
    }
    

    @Test
    void testGetSupervisedTraineeshipsService_SupervisesTraineeships() {
        TraineeshipPosition trainpos1 = new TraineeshipPosition();
        TraineeshipPosition trainpos2 = new TraineeshipPosition();
        when(principal.getName()).thenReturn(testProfessor.getId());
        List<TraineeshipPosition> mockPositions = List.of(trainpos1, trainpos2);
        when(traineeshipPositionMapper.findByProfessor_Id(testProfessor.getId())).thenReturn(mockPositions);
        professorService.getSupervisedTraineeshipsService(principal, model);
        verify(model).addAttribute("supervisedTraineeships", mockPositions);
    }

    @Test
    void testGetSupervisedTraineeshipsService_DoesNotSuperviseTraineeships() {
        when(principal.getName()).thenReturn(testProfessor.getId());
        when(traineeshipPositionMapper.findByProfessor_Id(testProfessor.getId()))
                .thenThrow(new EntityNotFoundException("Positions not found"));
        try {
            professorService.getSupervisedTraineeshipsService(principal, model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Positions not found", e.getMessage());
        }
    }
    
    @Test
    void testSaveEvaluationService() {
        int traineeshipId = 1;
        when(principal.getName()).thenReturn(testProfessor.getId());
        when(professorMapper.getReferenceById(testProfessor.getId())).thenReturn(testProfessor);
        testEvaluation = new EvaluationProfessor();
        when(evaluationProfessorMapper.findByTraineeshipPosition_Id(traineeshipId))
            .thenReturn(testEvaluation);
        testEvaluation.setEffectivenessGrade(4);
        professorService.saveEvaluationService(testEvaluation, traineeshipId, principal);
        assertEquals(4, testEvaluation.getEffectivenessGrade());
        verify(evaluationProfessorMapper).save(testEvaluation);
    }
}