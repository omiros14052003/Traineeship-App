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

import myy803.traineeshipapp.controllers.StudentController;
import myy803.traineeshipapp.services.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        mockPrincipal = () -> "testStudent";
    }

    @Test
    void testStudentDashboard() throws Exception {
        mockMvc.perform(get("/student/dashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("student/dashboard"));
    }

    @Test
    void testShowStudentProfile() throws Exception {
        mockMvc.perform(get("/student/profile").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("student/profile"));
    }

    @Test
    void testSaveStudentProfile() throws Exception {
        mockMvc.perform(post("/student/save_profile")
               .principal(mockPrincipal))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/student/dashboard"));
    }

    @Test
    void testShowLogbook() throws Exception {
        mockMvc.perform(get("/student/logbook").principal(mockPrincipal))
               .andExpect(status().isOk())
               .andExpect(view().name("student/logbook"));
    }

    @Test
    void testSaveLogbook() throws Exception {
        mockMvc.perform(post("/student/save_logbook")
               .param("id", "1")
               .param("logbook", "logbook_entry_1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/student/logbook"));
    }
}
