package com.physilink.controller.ui;


import com.physilink.controller.command.AdminSignupFormCommand;
import com.physilink.dto.model.user.UserDto;
import com.physilink.service.TaskService;
import com.physilink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Vignesh G
 */

@Controller
public class AdminController {

    @Autowired
    TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping(value = {"/logout"})
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:login";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "redirect:dashboard";
    }

    @GetMapping(value = "/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("adminSignupFormData", new AdminSignupFormCommand());
        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createNewAdmin(@Valid @ModelAttribute("adminSignupFormData") AdminSignupFormCommand adminSignupFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("signup");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            try {
                UserDto newUser = registerUser(adminSignupFormCommand);
            } catch (Exception exception) {
                bindingResult.rejectValue("email", "error.adminSignupFormCommand", exception.getMessage());
                return modelAndView;
            }
        }
        return new ModelAndView("login");
    }

    /**
     * Register a new user in the database
     *
     * @param adminSignupRequest
     * @return
     */
    private UserDto registerUser(@Valid AdminSignupFormCommand adminSignupRequest) {
        UserDto userDto = new UserDto()
                .setEmail(adminSignupRequest.getEmail())
                .setPassword(adminSignupRequest.getPassword())
                .setFirstName(adminSignupRequest.getFirstName())
                .setLastName(adminSignupRequest.getLastName())
                .setMobileNumber(adminSignupRequest.getMobileNumber())
                .setAdmin(adminSignupRequest.getRole().equals("ADMIN"))
                .setAidos(adminSignupRequest.getRole().equals("AIDOS"))
                .setCt(adminSignupRequest.getRole().equals("CT"))
                .setMedIns(adminSignupRequest.getRole().equals("MEDINS"));

        return userService.signup(userDto);
    }
}