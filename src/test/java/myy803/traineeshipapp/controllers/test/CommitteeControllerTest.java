package myy803.traineeshipapp.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import myy803.traineeshipapp.controllers.CommitteeController;
import myy803.traineeshipapp.services.CommitteeStudentService;
import myy803.traineeshipapp.services.CommitteeTraineeshipService;

@ExtendWith(MockitoExtension.class)
class CommitteeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommitteeStudentService committeeStudentService;

    @Mock
    private CommitteeTraineeshipService committeeTraineeshipService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CommitteeController committeeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(committeeController).build();
    }

    @Test
    void testCommitteeDashboard() throws Exception {
        mockMvc.perform(get("/committee/dashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("committee/dashboard"));
    }

    @Test
    void testGetInterestedStudents() throws Exception {
        mockMvc.perform(get("/committee/interested_students_list"))
               .andExpect(status().isOk())
               .andExpect(view().name("committee/interested_students_list"));
    }

    @Test
    void testShowMatchingOffers() throws Exception {
        mockMvc.perform(get("/committee/show_matching_offers")
               .param("student_id", "student1")
               .param("strategy", "strategy1"))
               .andExpect(status().isOk())
               .andExpect(view().name("committee/show_matching_offers"));
    }

    @Test
    void testAssignStudentToPosition() throws Exception {
        mockMvc.perform(post("/committee/assign_student_to_position")
               .param("student_id", "student1")
               .param("position_id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/committee/interested_students_list"));
    }

    @Test
    void testGetTraineeshipsInProgress() throws Exception {
        mockMvc.perform(get("/committee/traineeships_in_progress"))
               .andExpect(status().isOk())
               .andExpect(view().name("committee/traineeships_in_progress"));
    }

    @Test
    void testShowMatchingProfessors() throws Exception {
        mockMvc.perform(get("/committee/show_matching_professors")
               .param("position_id", "1")
               .param("strategy", "strategy1"))
               .andExpect(status().isOk())
               .andExpect(view().name("committee/show_matching_professors"));
    }

    @Test
    void testAssignProfessorToPosition() throws Exception {
        mockMvc.perform(post("/committee/assign_professor_to_traineeship_position")
               .param("professor_id", "prof1")
               .param("position_id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/committee/traineeships_in_progress"));
    }

    @Test
    void testShowTraineeshipForMarking() throws Exception {
        mockMvc.perform(get("/committee/show_traineeship_for_marking")
               .param("position_id", "1"))
               .andExpect(status().isOk())
               .andExpect(view().name("committee/show_traineeship_for_marking"));
    }

    @Test
    void testSubmitTraineeshipGrade_Passed() throws Exception {
        mockMvc.perform(post("/committee/submit_traineeship_grade")
               .param("mark", "passed")
               .param("position_id", "1"))
               .andExpect(redirectedUrl("/committee/traineeships_in_progress"));
    }

    @Test
    void testSubmitTraineeshipGrade_Failed() throws Exception {
        mockMvc.perform(post("/committee/submit_traineeship_grade")
               .param("mark", "failed")
               .param("position_id", "1"))
               .andExpect(redirectedUrl("/committee/traineeships_in_progress"));
    }
}
