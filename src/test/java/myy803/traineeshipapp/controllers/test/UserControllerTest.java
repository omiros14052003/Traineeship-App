package myy803.traineeshipapp.controllers.test;

import static org.mockito.Mockito.*;
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

import myy803.traineeshipapp.controllers.UserController;
import myy803.traineeshipapp.datamodel.Role;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testLoginUser() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk())
               .andExpect(view().name("auth/login"));
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(get("/register"))
               .andExpect(status().isOk())
               .andExpect(view().name("auth/register"));
    }

    @Test
    void testSaveUser_Success() throws Exception {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setRole(Role.STUDENT);

        when(userService.processRegistrationService(any(User.class), any()))
            .thenReturn("auth/login");

        mockMvc.perform(post("/save")
               .flashAttr("user", testUser))
               .andExpect(status().isOk())
               .andExpect(view().name("auth/login"));

        verify(userService).processRegistrationService(any(User.class), any());
    }

    @Test
    void testSaveUser_WithErrors() throws Exception {
        when(userService.processRegistrationService(any(User.class), any()))
            .thenReturn("auth/register");

        mockMvc.perform(post("/save")
               .flashAttr("user", new User()))
               .andExpect(status().isOk())
               .andExpect(view().name("auth/register"));
    }
}
