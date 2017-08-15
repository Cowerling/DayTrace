package com.cowerling.daytrace.web;

import com.cowerling.daytrace.data.UserRepository;
import com.cowerling.daytrace.domain.user.User;
import com.cowerling.daytrace.domain.user.UserOperation;
import com.cowerling.daytrace.domain.user.UserOperationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/")
@SessionAttributes("loginUser")
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        if (!model.containsAttribute("loginUser")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
            User loginUser = userRepository.findUserByName(name);

            userRepository.saveUserOperationRecord(new UserOperationRecord(loginUser.getId(), UserOperation.LOGIN, new Date(), "Login at ip " + request.getRemoteAddr()));
            model.addAttribute("loginUser", userRepository.findUserByName(name));
        }

        return "home";
    }

    @RequestMapping(value = "2", method = RequestMethod.GET)
    public String home2(Model model, HttpServletRequest request) {
        model.addAttribute("registerUser", new User());

        return "home2";
    }
}
