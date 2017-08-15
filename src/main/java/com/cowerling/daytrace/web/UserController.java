package com.cowerling.daytrace.web;

import com.cowerling.daytrace.annotation.ToResourceNotFound;
import com.cowerling.daytrace.data.UserRepository;
import com.cowerling.daytrace.domain.user.User;
import com.cowerling.daytrace.domain.user.UserProfile;
import com.cowerling.daytrace.domain.user.UserProfileForm;
import com.cowerling.daytrace.security.PasswordEncoderService;
import com.cowerling.daytrace.web.exception.DuplicateUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@SessionAttributes({"loginUser"})
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            throw new SessionAuthenticationException("Can not register after login, please logout to register.");
        }

        model.addAttribute("registerUser", new User());
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(RedirectAttributes model, @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return "user/register";
        }

        try {
            user.setPassword(passwordEncoderService.encode(user.getPassword()));

            userRepository.saveUser(user);
            userRepository.saveUserProfile(new UserProfile(user.getId()));
        } catch (DuplicateUserException e) {
            errors.reject(e.getClass().getName(), e.getMessage());
            return "user/register";
        }

        model.addFlashAttribute("name",user.getName());

        return "redirect:/user/registerSuccess";
    }

    @RequestMapping("/registerSuccess")
    @PreAuthorize("#model.containsAttribute('name')")
    @ToResourceNotFound
    public String registerSuccess(Model model) {
        return "user/registerSuccess";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showProfile(@ModelAttribute("loginUser") User loginUser, Model model) {
        model.addAttribute(userRepository.findUserProfileByUserId(loginUser.getId()));
        model.addAttribute(userRepository.findUserMedalsByUserId(loginUser.getId()));
        model.addAttribute(userRepository.findUserOperationRecordsByUserId(loginUser.getId()));
        return "user/profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String processProfile(UserProfileForm userProfileForm, Model model) {
        return "user/profile";
    }
}
