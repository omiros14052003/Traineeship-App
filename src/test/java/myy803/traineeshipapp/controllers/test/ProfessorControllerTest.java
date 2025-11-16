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

import myy803.traineeshipapp.controllers.ProfessorController;
import myy803.traineeshipapp.datamodel.EvaluationProfessor;
import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.services.ProfessorService;

@ExtendWith(MockitoExtension.class)
class ProfessorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController professorController;

    private Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(professorController).build();
        mockPrincipal = () -> "testProfessor";
    }

    @Test
    void testProfessorDashboard() throws Exception {
        mockMvc.perform(get("/professor/dashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("professor/dashboard"));
    }

    @Test
    void testGetSupervisedTraineeships() throws Exception {
        mockMvc.perform(get("/professor/supervised_positions").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("professor/supervised_positions"));
    }

    @Test
    void testShowEvaluationForm() throws Exception {
        mockMvc.perform(get("/professor/supervised_positions/evaluate")
               .param("traineeshipId", "1")
               .principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("professor/professor_evaluation"));
    }

    @Test
    void testSaveEvaluation() throws Exception {
        mockMvc.perform(post("/professor/supervised_positions/evaluate/save")
               .param("traineeshipId", "1")
               .flashAttr("evaluation", new EvaluationProfessor())
               .principal(mockPrincipal))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/professor/supervised_positions"));
    }

    @Test
    void testShowProfessorProfile() throws Exception {
        mockMvc.perform(get("/professor/profile").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("professor/profile"));
    }

    @Test
    void testSaveProfessorProfile() throws Exception {
        mockMvc.perform(post("/professor/save_profile")
               .flashAttr("professor", new Professor())
               .principal(mockPrincipal))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/professor/dashboard"));
    }
}
