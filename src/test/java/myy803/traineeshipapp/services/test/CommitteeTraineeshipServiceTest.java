package myy803.traineeshipapp.services.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.*;
import myy803.traineeshipapp.datamodel.matchingProfessorStrategies.MatchingProfessorFactory;
import myy803.traineeshipapp.datamodel.matchingProfessorStrategies.MatchingProfessorStrategy;
import myy803.traineeshipapp.mappers.*;
import myy803.traineeshipapp.services.CommitteeTraineeshipServiceImpl;

@ExtendWith(MockitoExtension.class)
class CommitteeTraineeshipServiceTest {

    @Mock private ProfessorMapper professorMapper;
    @Mock private TraineeshipOfferMapper traineeshipOfferMapper;
    @Mock private TraineeshipPositionMapper traineeshipPositionMapper;
    @Mock private EvaluationProfessorMapper evaluationProfessorMapper;
    @Mock private EvaluationCompanyMapper evaluationCompanyMapper;
    @Mock private MatchingProfessorFactory matchingProfessorFactory;
    @Mock private Model model;
    
    @InjectMocks
    private CommitteeTraineeshipServiceImpl committeeService;
    
    private Professor testProfessor;
    private TraineeshipPosition testPosition;
    private TraineeshipOffer testOffer;
    private EvaluationProfessor testEvalProfessor;
    private EvaluationCompany testEvalCompany;

    @BeforeEach
    void setUp() {
        testProfessor = new Professor();
        testProfessor.setId("prof1");
        
        testPosition = new TraineeshipPosition();
        testPosition.setId(1);
        testPosition.setProfessor(testProfessor);
        
        testPosition = new TraineeshipPosition();
        testPosition.setId(1);
        testPosition.setStudent(new Student());
        
        testOffer = new TraineeshipOffer();
        testOffer.setId(1);
    }

    @Test
    void testGetTraineeshipsInProgressService_TraineeshipsExist() {
        when(traineeshipPositionMapper.findByStudentIsNotNullAndProfessorIsNull())
            .thenReturn(List.of(testPosition));
        when(traineeshipPositionMapper.findByStudentIsNotNullAndProfessorIsNotNullAndMark(Mark.NOT_MARKED))
            .thenReturn(List.of(testPosition));
        committeeService.getTraineeshipsInProgressService(model);
        verify(model).addAttribute("traineeships_without_supervisor", List.of(testPosition));
        verify(model).addAttribute("traineeships_without_marks", List.of(testPosition));
    }

    @Test
    void testGetTraineeshipsInProgressService_TraineeshipsDontExist() {
        when(traineeshipPositionMapper.findByStudentIsNotNullAndProfessorIsNull())
                .thenThrow(new EntityNotFoundException("Traineeships not found"));
        try {
            committeeService.getTraineeshipsInProgressService(model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Traineeships not found", e.getMessage());
        }
        verify(model, never()).addAttribute(eq("traineeships_without_supervisor"), any());
        verify(model, never()).addAttribute(eq("traineeships_without_marks"), any());
    }

    @Test
    void testShowMatchingProfessorsService_ProfessorsExist_ExactMatch() {
        List<Professor> professors = List.of(testProfessor);
        MatchingProfessorStrategy mockStrategy = mock(MatchingProfessorStrategy.class);
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        when(traineeshipOfferMapper.getReferenceByTraineeshipPosition(testPosition)).thenReturn(testOffer);
        when(professorMapper.findAll()).thenReturn(professors);
        when(matchingProfessorFactory.create("strategy1")).thenReturn(mockStrategy);
        when(mockStrategy.findMatchingTraineeshipsForProfessor(testOffer, professors)).thenReturn(professors);
        committeeService.showMatchingProfessorsService(1, "strategy1", model);
        verify(model).addAttribute("professors", professors);
        verify(model).addAttribute("position_id", 1);
    }


    @Test
    void testShowMatchingProfessorsService_ProfessorsDontExist() {
        MatchingProfessorStrategy mockStrategy = mock(MatchingProfessorStrategy.class);
        when(professorMapper.findAll()).thenReturn(null);
        when(matchingProfessorFactory.create("strategy1")).thenReturn(mockStrategy);
        when(mockStrategy.findMatchingTraineeshipsForProfessor(any(), isNull()))
            .thenThrow(new EntityNotFoundException("Professors not found"));
        try {
            committeeService.showMatchingProfessorsService(1, "strategy1", model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Professors not found", e.getMessage());
        }
        verify(model, never()).addAttribute(eq("professors"), any());
        verify(model, never()).addAttribute(eq("position_id"), any());
    }

    @Test
    void testAssignProfessorToPositionService_PositionIsNotTaken() {
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        when(professorMapper.getReferenceById("prof1")).thenReturn(testProfessor);
        boolean result = committeeService.assignProfessorToPositionService(1, "prof1");
        assertTrue(result);
        assertEquals(testProfessor, testPosition.getProfessor());
        assertEquals(1, testProfessor.getSuperviseeNum());
        verify(traineeshipPositionMapper).save(testPosition);
        verify(professorMapper).save(testProfessor);
        verify(evaluationProfessorMapper).save(any(EvaluationProfessor.class));
    }

    @Test
    void testAssignStudentToPositionService_PositionIsTaken() {
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        testPosition.setProfessor(new Professor());
        boolean result = committeeService.assignProfessorToPositionService(1, "prof1");
        assertFalse(result);
        verify(traineeshipPositionMapper, never()).save(any());
        verify(professorMapper, never()).save(any());
        verify(evaluationProfessorMapper, never()).save(any());
    }

    @Test
    void testShowTraineeshipForMarkingService() {
        testEvalProfessor = new EvaluationProfessor();
        testEvalProfessor.setId(1);
        testEvalProfessor.setTraineeshipPosition(testPosition);
        testEvalCompany = new EvaluationCompany();
        testEvalCompany.setId(1);
        testEvalCompany.setTraineeshipPosition(testPosition);
        when(evaluationCompanyMapper.findByTraineeshipPosition_Id(1)).thenReturn(testEvalCompany);
        when(evaluationProfessorMapper.findByTraineeshipPosition_Id(1)).thenReturn(testEvalProfessor);
        
        testEvalCompany.setEffectivenessGrade(3);
        testEvalCompany.setEfficiencyGrade(4);
        testEvalCompany.setMotivationGrade(5);
        testEvalProfessor.setEffectivenessGrade(1);
        testEvalProfessor.setEfficiencyGrade(2);
        testEvalProfessor.setMotivationGrade(3);
        committeeService.showTraineeshipForMarkingService(1, model);
        verify(model).addAttribute("evaluation_company", testEvalCompany);
        verify(model).addAttribute("evaluation_professor", testEvalProfessor);
        verify(model).addAttribute("position_id", 1);
        verify(model).addAttribute("avgGrade", 3);
    }

    @Test
    void testSubmitTraineeshipGradeService_Success() {
        testPosition.setProfessor(testProfessor);
        testProfessor.setSuperviseeNum(1);
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        committeeService.submitTraineeshipGradeService("passed", 1);
        assertEquals(Mark.PASSED, testPosition.getMark());
        assertEquals(0, testProfessor.getSuperviseeNum());
        verify(traineeshipPositionMapper).save(testPosition);
        verify(professorMapper).save(testProfessor);
    }

    @Test
    void testSubmitTraineeshipGradeService_Failure() {
        testPosition.setProfessor(testProfessor);
        testProfessor.setSuperviseeNum(1);
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        committeeService.submitTraineeshipGradeService("failed", 1);
        assertEquals(Mark.FAILED, testPosition.getMark());
        assertEquals(0, testProfessor.getSuperviseeNum());
        verify(traineeshipPositionMapper).save(testPosition);
        verify(professorMapper).save(testProfessor);
    }
}