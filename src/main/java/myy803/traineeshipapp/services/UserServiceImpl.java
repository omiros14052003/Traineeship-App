package myy803.traineeshipapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.Company;
import myy803.traineeshipapp.datamodel.Professor;
import myy803.traineeshipapp.datamodel.Student;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.mappers.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CompanyService companyService;

    @Override
    public void registerUserService(Model model) {
        model.addAttribute("user", new User());
    }

    @Override
    public String processRegistrationService(User user, Model model) {
        if (isUserPresent(user)) {
            model.addAttribute("successMessage", "User already registered!");
            return "auth/login";
        }
        
        if (user.getRole() == null) {
            model.addAttribute("errorMessage", "Role must be selected.");
            return "auth/register";
        }

        saveUser(user);
        model.addAttribute("successMessage", "User registered successfully!");
        return "auth/login";
    }

    @Override
    public boolean isUserPresent(User user) {
        User storedUser = userMapper.findByUsername(user.getUsername());
        return storedUser != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userMapper.findByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(String.format("User not found with username: %s", username));
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userMapper.findByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(String.format("User not found with username: %s", username));
        }
    }
    
    public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userMapper.save(user);
        
        if (user.getRole() == myy803.traineeshipapp.datamodel.Role.PROFESSOR) {
            professorService.saveProfessorAccountService(new Professor(), user.getUsername());
        } else if(user.getRole() == myy803.traineeshipapp.datamodel.Role.COMPANY) {
            companyService.saveCompanyAccountService(new Company(), user.getUsername());
        } else if(user.getRole() == myy803.traineeshipapp.datamodel.Role.STUDENT) {
            studentService.saveStudentAccountService(new Student(), user.getUsername());
        }
    }
}