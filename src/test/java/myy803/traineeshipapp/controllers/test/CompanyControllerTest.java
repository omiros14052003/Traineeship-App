package myy803.traineeshipapp.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import myy803.traineeshipapp.controllers.CompanyController;
import myy803.traineeshipapp.services.CompanyService;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
        mockPrincipal = () -> "testCompany";
    }

    @Test
    void testCompanyDashboard() throws Exception {
        mockMvc.perform(get("/company/dashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("company/dashboard"));
    }

    @Test
    void testShowCompanyProfile() throws Exception {
        mockMvc.perform(get("/company/profile").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("company/profile"));
    }

    @Test
    void testSaveCompanyProfile() throws Exception {
        mockMvc.perform(post("/company/save_profile")
               .param("name", "Test Company")
               .param("id", "1")
               .principal(mockPrincipal))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/company/dashboard"));
    }

    @Test
    void testGetOffersByCompany() throws Exception {
        mockMvc.perform(get("/company/open_positions").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("company/open_positions"));
    }

    @Test
    void testShowCreateOfferForm() throws Exception {
        mockMvc.perform(get("/company/open_positions/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("company/create_offer"));
    }

    @Test
    void testSaveOffer() throws Exception {
        mockMvc.perform(post("/company/open_positions/save")
               .param("title", "Software Internship")
               .principal(mockPrincipal))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/company/open_positions"));
    }

    @Test
    void testDeleteOffer() throws Exception {
        mockMvc.perform(get("/company/open_positions/delete_offer")
               .param("offer_id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/company/open_positions"));
    }

    @Test
    void testDeletePosition() throws Exception {
        mockMvc.perform(get("/company/open_positions/delete_position")
               .param("position_id", "2"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/company/open_positions"));
    }

    @Test
    void testGetAssignedTraineeships() throws Exception {
        mockMvc.perform(get("/company/assigned_positions").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("company/assigned_positions"));
    }

    @Test
    void testShowEvaluationForm() throws Exception {
        mockMvc.perform(get("/company/assigned_positions/evaluate")
               .param("traineeshipId", "10")
               .principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("company/company_evaluation"));
    }

    @Test
    void testSaveEvaluation() throws Exception {
        mockMvc.perform(post("/company/assigned_positions/evaluate/save")
               .param("traineeshipPosition.id", "100")
               .principal(mockPrincipal))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/company/assigned_positions"));
    }
}
