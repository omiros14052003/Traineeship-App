package myy803.traineeshipapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.services.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String loginUser() {
        return "auth/login";
    }

    @RequestMapping("/register")
    public String registerUser(Model model) {
        userService.registerUserService(model);
        return "auth/register";
    }

    @RequestMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        return userService.processRegistrationService(user, model);
    }
}