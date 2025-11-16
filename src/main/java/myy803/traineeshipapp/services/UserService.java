package myy803.traineeshipapp.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import myy803.traineeshipapp.datamodel.User;

public interface UserService extends UserDetailsService {
    
    void registerUserService(Model model);
    
    boolean isUserPresent(User user);
    
    User findByUsername(String username);
    
    String processRegistrationService(User user, Model model);
}