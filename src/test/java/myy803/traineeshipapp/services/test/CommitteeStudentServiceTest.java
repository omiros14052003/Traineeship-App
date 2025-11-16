package myy803.traineeshipapp.services.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
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
import myy803.traineeshipapp.datamodel.matchingStudentStrategies.MatchingStudentFactory;
import myy803.traineeshipapp.datamodel.matchingStudentStrategies.MatchingStudentStrategy;
import myy803.traineeshipapp.mappers.*;
import myy803.traineeshipapp.services.CommitteeStudentServiceImpl;

@ExtendWith(MockitoExtension.class)
class CommitteeStudentServiceTest {

    @Mock private StudentMapper studentMapper;
    @Mock private TraineeshipOfferMapper traineeshipOfferMapper;
    @Mock private TraineeshipPositionMapper traineeshipPositionMapper;
    @Mock private EvaluationCompanyMapper evaluationCompanyMapper;
    @Mock private MatchingStudentFactory matchingStudentFactory;
    @Mock private Model model;
    
    @InjectMocks
    private CommitteeStudentServiceImpl committeeStudentService;
    
    private Student testStudent;
    private TraineeshipPosition testPosition;
    private TraineeshipOffer testOffer;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId("student1");
        testStudent.setLookingForPosition(true);
        
        testPosition = new TraineeshipPosition();
        testPosition.setId(1);
        
        testOffer = new TraineeshipOffer();
        testOffer.setId(1);
    }

    @Test
    void testGetInterestedStudentsService_InterestedStudentsExist() {
        when(studentMapper.getByLookingForPosition(true))
            .thenReturn(Arrays.asList(testStudent));
        committeeStudentService.getInterestedStudentsService(model);
        verify(model).addAttribute("students", Arrays.asList(testStudent));
    }

    @Test
    void testGetInterestedStudentsService_InterestedStudentsDontExist() {
        when(studentMapper.getByLookingForPosition(true))
                .thenThrow(new EntityNotFoundException("Students not found"));
        try {
            committeeStudentService.getInterestedStudentsService(model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Students not found", e.getMessage());
        }
        verify(model, never()).addAttribute(eq("students"), any());
    }

    @Test
    void testShowMatchingOffersService_OffersExist() {
        List<TraineeshipOffer> allOffers = Arrays.asList(testOffer);
        MatchingStudentStrategy mockStrategy = mock(MatchingStudentStrategy.class);
        when(studentMapper.getReferenceById("student1")).thenReturn(testStudent);
        when(traineeshipOfferMapper.findByTraineeshipPositionStudentIsNull()).thenReturn(allOffers);
        when(matchingStudentFactory.create("strategy1")).thenReturn(mockStrategy);
        when(mockStrategy.findMatchingTraineeshipsForStudent(testStudent, allOffers))
            .thenReturn(allOffers);
        committeeStudentService.showMatchingOffersService("student1", "strategy1", model);
        verify(model).addAttribute("offers", allOffers);
        verify(model).addAttribute("student_id", "student1");
    }

    @Test
    void testShowMatchingOffersService_OffersDontExist() {
        MatchingStudentStrategy mockStrategy = mock(MatchingStudentStrategy.class);
        when(studentMapper.getReferenceById("student1")).thenReturn(testStudent);
        when(traineeshipOfferMapper.findByTraineeshipPositionStudentIsNull()).thenReturn(List.of());
        when(matchingStudentFactory.create("strategy1")).thenReturn(mockStrategy);
        when(mockStrategy.findMatchingTraineeshipsForStudent(testStudent, List.of()))
                .thenThrow(new EntityNotFoundException("Offers not found"));
        try {
            committeeStudentService.showMatchingOffersService("student1", "strategy1", model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Offers not found", e.getMessage());
        }
        verify(model,never()).addAttribute(eq("offers"), any());
        verify(model, never()).addAttribute(eq("student_id"),eq("student1"));
    }

    @Test
    void testAssignStudentToPositionService_PositionIsNotTaken() {
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        when(studentMapper.getReferenceById("student1")).thenReturn(testStudent);
        boolean result = committeeStudentService.assignStudentToPositionService("student1", 1);
        assertTrue(result);
        assertEquals(testStudent, testPosition.getStudent());
        assertFalse(testStudent.getLookingForPosition());
        verify(traineeshipPositionMapper).save(testPosition);
        verify(evaluationCompanyMapper).save(any(EvaluationCompany.class));
    }

    @Test
    void testAssignStudentToPositionService_PositionIsTaken() {
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        testPosition.setStudent(new Student());
        boolean result = committeeStudentService.assignStudentToPositionService("student1", 1);
        assertFalse(result);
        verify(traineeshipPositionMapper, never()).save(any());
        verify(evaluationCompanyMapper, never()).save(any());
    }
}