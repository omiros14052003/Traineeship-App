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

import myy803.traineeshipapp.datamodel.*;
import myy803.traineeshipapp.mappers.*;
import myy803.traineeshipapp.services.CompanyServiceImpl;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock private CompanyMapper companyMapper;
    @Mock private TraineeshipOfferMapper traineeshipOfferMapper;
    @Mock private TraineeshipPositionMapper traineeshipPositionMapper;
    @Mock private EvaluationCompanyMapper evaluationCompanyMapper;
    @Mock private UserMapper userMapper;
    @Mock private Model model;
    @Mock private Principal principal;
    
    @InjectMocks
    private CompanyServiceImpl companyService;
    
    private Company testCompany;
    private TraineeshipOffer testOffer;
    private TraineeshipPosition testPosition;
    private EvaluationCompany testEvaluation;

    @BeforeEach
    void setUp() {
        testCompany = new Company();
        testCompany.setId("company1");
    }

    @Test
    void testShowCompanyProfileService() {
        when(companyMapper.getReferenceById("company1")).thenReturn(testCompany);
        companyService.showCompanyProfileService("company1", model);
        verify(model).addAttribute("company", testCompany);
    }

    @Test
    void testSaveCompanyProfileService_Success() {
        companyService.saveCompanyProfileService(testCompany, "company1");
        verify(companyMapper).save(testCompany);
    }

    @Test
    void testSaveCompanyProfileService_Failure() {
        doThrow(new RuntimeException("Save failed")).when(companyMapper).save(testCompany);
        try {
            companyService.saveCompanyProfileService(testCompany, "company1");
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Save failed", e.getMessage());
        }
    }
    
    @Test
    void testSaveCompanyAccountService() {
        User testUser = new User();
        testUser.setUsername("company2");
        Company newCompany = new Company();
        when(userMapper.findByUsername("company2")).thenReturn(testUser);
        companyService.saveCompanyAccountService(newCompany, "company2");
        assertEquals("company2", newCompany.getId()); 
        verify(companyMapper).save(newCompany);
    }

    @Test
    void testGetOffersByCompanyService_HasOffers() {
        testOffer = new TraineeshipOffer();
        testPosition = new TraineeshipPosition();
        when(principal.getName()).thenReturn("company1");
        when(traineeshipOfferMapper.findByCompany_Id("company1")).thenReturn(List.of(testOffer));
        when(traineeshipPositionMapper.findByCompany_Id("company1")).thenReturn(List.of(testPosition));
        companyService.getOffersByCompanyService(principal, model);
        verify(model).addAttribute("offers", List.of(testOffer));
        verify(model).addAttribute("positions", List.of(testPosition));
    }

    @Test
    void testGetOffersByCompanyService_DoesNotHaveOffers() {
        when(principal.getName()).thenReturn("company1");
        when(traineeshipOfferMapper.findByCompany_Id("company1"))
                .thenThrow(new EntityNotFoundException("Offers not found"));
        try {
            companyService.getOffersByCompanyService(principal, model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Offers not found", e.getMessage());
        }
        verify(model, never()).addAttribute(eq("offers"), any());
        verify(model, never()).addAttribute(eq("positions"), any());
    }

    @Test
    void testShowCreateOfferFormService() {
        companyService.showCreateOfferFormService(model);
        verify(model).addAttribute(eq("traineeshipOffer"), any(TraineeshipOffer.class));
    }

    @Test
    void testSaveOfferService() {
        TraineeshipOffer testOffer = new TraineeshipOffer();
        testOffer.setId(1);
        when(principal.getName()).thenReturn("company1");
        when(companyMapper.getReferenceById("company1")).thenReturn(testCompany);
        companyService.saveOfferService(testOffer, principal);
        assertEquals(testCompany, testOffer.getCompany());
        verify(traineeshipOfferMapper).save(testOffer);
        verify(traineeshipPositionMapper).save(any(TraineeshipPosition.class));
    }

    @Test
    void testDeleteOfferService() {
        TraineeshipOffer testOffer = new TraineeshipOffer();
        testOffer.setId(1);
        when(traineeshipOfferMapper.getReferenceById(1)).thenReturn(testOffer);
        companyService.deleteOfferService(1);
        verify(traineeshipOfferMapper).delete(testOffer);
    }

    @Test
    void testDeletePositionService() {
    	TraineeshipPosition testPosition = new TraineeshipPosition();
        testPosition.setId(1);
        when(traineeshipPositionMapper.getReferenceById(1)).thenReturn(testPosition);
        companyService.deletePositionService(1);
        verify(traineeshipPositionMapper).delete(testPosition);
    }

    @Test
    void testGetAssignedTraineeshipsService_TraineeshipsExist() {
    	TraineeshipPosition testPosition = new TraineeshipPosition();
        testPosition.setId(1);
        when(principal.getName()).thenReturn("company1");
        when(traineeshipPositionMapper.findByStudentIsNotNullAndCompany_Id("company1"))
            .thenReturn(List.of(testPosition));
        companyService.getAssignedTraineeshipsService(principal, model);
        verify(model).addAttribute("assignedTraineeships", List.of(testPosition));
    }

    @Test
    void testGetAssignedTraineeshipsService_TraineeshipsDontExist() {
        when(principal.getName()).thenReturn("company1");
        when(traineeshipPositionMapper.findByStudentIsNotNullAndCompany_Id("company1"))
                .thenThrow(new EntityNotFoundException("Positions not found"));
        try {
            companyService.getAssignedTraineeshipsService(principal, model);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Positions not found", e.getMessage());
        }
        verify(model, never()).addAttribute(eq("assignedTraineeships"), any());
    }

    @Test
    void testShowEvaluationsService() {
        int positionId = 1;
        testEvaluation = new EvaluationCompany();
        TraineeshipPosition mockPosition = new TraineeshipPosition();
        when(evaluationCompanyMapper.findByTraineeshipPosition_Id(positionId))
            .thenReturn(testEvaluation);
        when(traineeshipPositionMapper.getReferenceById(positionId))
            .thenReturn(mockPosition);
        companyService.showEvaluationsService(positionId, principal, model);
        verify(evaluationCompanyMapper).findByTraineeshipPosition_Id(positionId);
        verify(traineeshipPositionMapper).getReferenceById(positionId);
        List<Integer> expectedMarks = List.of(1, 2, 3, 4, 5);
        verify(model).addAttribute("marks", expectedMarks);
        verify(model).addAttribute("evaluation", testEvaluation);
        verify(model).addAttribute("grade", mockPosition.getMark());
    }

    @Test
    void testSaveEvaluationService() {
        int traineeshipId = 1;
        when(principal.getName()).thenReturn(testCompany.getId());
        when(companyMapper.getReferenceById(testCompany.getId())).thenReturn(testCompany);
        testEvaluation = new EvaluationCompany();
        when(evaluationCompanyMapper.findByTraineeshipPosition_Id(traineeshipId))
            .thenReturn(testEvaluation);
        testEvaluation.setEfficiencyGrade(4);
        companyService.saveEvaluationService(testEvaluation, traineeshipId, principal);
        assertEquals(4, testEvaluation.getEfficiencyGrade());
        verify(evaluationCompanyMapper).save(testEvaluation);
    }
}