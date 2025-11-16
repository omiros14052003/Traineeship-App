package myy803.traineeshipapp.services.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.*;
import myy803.traineeshipapp.mappers.UserMapper;
import myy803.traineeshipapp.services.CompanyService;
import myy803.traineeshipapp.services.ProfessorService;
import myy803.traineeshipapp.services.StudentService;
import myy803.traineeshipapp.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Mock
    private ProfessorService professorService;
    
    @Mock
    private StudentService studentService;
    
    @Mock
    private CompanyService companyService;
    
    @Mock
    private Model model;
    
    @InjectMocks
    private UserServiceImpl userService;
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setRole(Role.PROFESSOR);
    }
    
    @Test
    void testRegisterUserService() {
        userService.registerUserService(model);
        verify(model).addAttribute(eq("user"), any(User.class));
    }
    
    @Test
    void testProcessRegistrationService_Success() {
        when(userMapper.findByUsername(testUser.getUsername())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");
        String result = userService.processRegistrationService(testUser, model);
        assertEquals("auth/login", result);
        verify(userMapper).save(testUser);
        verify(model).addAttribute("successMessage", "User registered successfully!");
        verify(professorService).saveProfessorAccountService(any(Professor.class), eq(testUser.getUsername()));
    }
    
    @Test
    void testProcessRegistrationService_UserExists() {
        when(userMapper.findByUsername(testUser.getUsername())).thenReturn(testUser);
        String result = userService.processRegistrationService(testUser, model);
        assertEquals("auth/login", result);
        verify(model).addAttribute("successMessage", "User already registered!");
        verify(userMapper, never()).save(any());
    }
    
    @Test
    void testProcessRegistrationService_MissingRole() {
        testUser.setRole(null);
        String result = userService.processRegistrationService(testUser, model);
        assertEquals("auth/register", result);
        verify(model).addAttribute("errorMessage", "Role must be selected.");
        verify(userMapper, never()).save(any());
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userMapper.findByUsername(testUser.getUsername())).thenReturn(testUser);
        UserDetails result = userService.loadUserByUsername(testUser.getUsername());
        assertEquals(testUser, result);
    }
    
    @Test
    void testLoadUserByUsername_UserNotFound() throws Exception {
        String username = testUser.getUsername();
        when(userMapper.findByUsername(username))
            .thenThrow(new EmptyResultDataAccessException(1));
        try {
            userService.loadUserByUsername(username);
            fail("Expected UsernameNotFoundException to be thrown");
        } catch (UsernameNotFoundException exception) {
            assertEquals(String.format("User not found with username: %s", username),
                        exception.getMessage());
        }
    }

    @Test
    void testFindByUsername_Success() {
        when(userMapper.findByUsername(testUser.getUsername())).thenReturn(testUser);
        User result = userService.findByUsername(testUser.getUsername());
        assertEquals(testUser, result);
    }

    @Test
    void testFindByUsername_UserNotFound() throws Exception {
        String username = testUser.getUsername();
        when(userMapper.findByUsername(username))
            .thenThrow(new EmptyResultDataAccessException(1));
        try {
            userService.findByUsername(username);
            fail("Expected UsernameNotFoundException to be thrown");
        } catch (UsernameNotFoundException exception) {
            assertEquals(String.format("User not found with username: %s", username),
                        exception.getMessage());
        }
    }
    
    @Test
    void testIsUserPresent_UserExists() {
        when(userMapper.findByUsername(testUser.getUsername())).thenReturn(testUser);
        boolean result = userService.isUserPresent(testUser);
        assertTrue(result);
    }
    
    @Test
    void testIsUserPresent_UserDoesNotExist() {
        when(userMapper.findByUsername(testUser.getUsername())).thenReturn(null);
        boolean result = userService.isUserPresent(testUser);
        assertFalse(result);
    }
    
    @Test
    void testSaveUser_ProfessorRole() {
        testUser.setRole(Role.PROFESSOR);
        when(bCryptPasswordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");
        userService.saveUser(testUser);
        verify(userMapper).save(testUser);
        verify(professorService).saveProfessorAccountService(any(Professor.class), eq(testUser.getUsername()));
        verify(studentService, never()).saveStudentAccountService(any(), any());
        verify(companyService, never()).saveCompanyAccountService(any(), any());
    }
    
    @Test
    void testSaveUser_StudentRole() {
        testUser.setRole(Role.STUDENT);
        when(bCryptPasswordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");
        userService.saveUser(testUser);
        verify(userMapper).save(testUser);
        verify(studentService).saveStudentAccountService(any(Student.class), eq(testUser.getUsername()));
        verify(professorService, never()).saveProfessorAccountService(any(), any());
        verify(companyService, never()).saveCompanyAccountService(any(), any());
    }
    
    @Test
    void testSaveUser_CompanyRole() {
        testUser.setRole(Role.COMPANY);
        when(bCryptPasswordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");
        userService.saveUser(testUser);
        verify(userMapper).save(testUser);
        verify(companyService).saveCompanyAccountService(any(Company.class), eq(testUser.getUsername()));
        verify(professorService, never()).saveProfessorAccountService(any(), any());
        verify(studentService, never()).saveStudentAccountService(any(), any());
    }
}