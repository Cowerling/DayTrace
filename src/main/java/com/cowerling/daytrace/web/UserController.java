package com.cowerling.daytrace.web;

import com.cowerling.daytrace.annotation.ToResourceNotFound;
import com.cowerling.daytrace.data.UserRepository;
import com.cowerling.daytrace.domain.user.*;
import com.cowerling.daytrace.domain.user.form.UserEditForm;
import com.cowerling.daytrace.domain.user.form.UserForm;
import com.cowerling.daytrace.domain.user.form.UserProfileForm;
import com.cowerling.daytrace.security.PasswordEncoderService;
import com.cowerling.daytrace.utils.ImageUtils;
import com.cowerling.daytrace.web.exception.DuplicateUserException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@SessionAttributes({"loginUser"})
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger  = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Value("${user.photo.location}")
    private String photo_location;

    @Value("${user.photo.width}")
    private int photo_width;

    @Value("${user.photo.height}")
    private int photo_height;

    @Value("${user.photo.format}")
    private String photo_format;

    private static final String DEFAULT_MANAGED_USERS_OFFSET = "0";
    private static final String DEFAULT_MANAGED_USERS_LIMIT = "25";

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
    public String processRegister(RedirectAttributes redirectAttributes, @Valid @ModelAttribute("registerUser") User user, Errors errors) {
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

        redirectAttributes.addFlashAttribute("name", user.getName());

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

    @RequestMapping(value = "/profile/settings", method = RequestMethod.POST)
    public String processProfile(@ModelAttribute("loginUser") User loginUser, UserProfileForm userProfileForm) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        userRepository.updateUserProfileByUserId(
                loginUser.getId(),
                userProfileForm.getAlias(),
                Arrays.stream(UserGender.values()).filter(x -> x.name().equals(userProfileForm.getGender())).findAny().isPresent() ? UserGender.valueOf(userProfileForm.getGender()) : null,
                StringUtils.isNotEmpty(userProfileForm.getBirthday()) ? dateFormat.parse(userProfileForm.getBirthday()) : null,
                userProfileForm.getPhone(),
                userProfileForm.getBrief());

        return "redirect:/user/profile";
    }

    @RequestMapping(value = "/profile/security", method = RequestMethod.POST)
    public String processProfileSecurity(@ModelAttribute("loginUser") User loginUser, @RequestPart("photo") MultipartFile photo, UserForm userForm) throws IOException {
        loginUser.setEmail(userForm.getEmail());

        if (!photo.isEmpty()) {
             loginUser.setPhoto(ImageUtils.compress(photo.getInputStream(), photo_width, photo_height, photo_format, photo_location));
        }

        if (StringUtils.isNotEmpty(userForm.getPassword())){
            loginUser.setPassword(passwordEncoderService.encode(userForm.getPassword()));

            userRepository.updateUser(loginUser);
        } else {
            userRepository.updateUserById(loginUser.getId(), loginUser.getEmail(), loginUser.getPhoto());
        }

        return "redirect:/user/profile";
    }

    @RequestMapping("/manage")
    @RolesAllowed({"ROLE_ADVAN_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @ToResourceNotFound
    public String manage(@ModelAttribute("loginUser") User loginUser) {
        return "/user/manage";
    }

    @RequestMapping("/manage/userCount")
    @RolesAllowed({"ROLE_ADVAN_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @ToResourceNotFound
    public @ResponseBody Long managedUsers(@ModelAttribute("loginUser") User loginUser) {
        return userRepository.findUserCountByRole(Arrays.stream(UserRole.values()).filter(x -> x.ordinal() > loginUser.getUserRole().ordinal()).toArray(size -> new UserRole[size]));
    }

    @RequestMapping(value = "/manage/users", method = RequestMethod.GET, produces = "application/json")
    @RolesAllowed({"ROLE_ADVAN_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @ToResourceNotFound
    public @ResponseBody List<User> managedUsers(@ModelAttribute("loginUser") final User loginUser, @RequestParam(value = "offset", defaultValue = DEFAULT_MANAGED_USERS_OFFSET) int offset, @RequestParam(value = "limit", defaultValue = DEFAULT_MANAGED_USERS_LIMIT) int limit) {
        return userRepository.findUsersByRole(Arrays.stream(UserRole.values()).filter(x -> x.ordinal() > loginUser.getUserRole().ordinal()).toArray(size -> new UserRole[size]), offset, limit);
    }

    @RequestMapping(value = "/manage/userProfileAndMedals", method = RequestMethod.GET, produces = "application/json")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PreAuthorize("hasPermission(#requestUserName, #loginUser)")
    @ToResourceNotFound
    public @ResponseBody Map<String, Object> managedUserProfileAndMedals(@ModelAttribute("loginUser") final User loginUser, @RequestParam("requestUserName") String requestUserName) {
        UserProfile userProfile = userRepository.findUserProfileByUserName(requestUserName);
        List<UserMedal> userMedals = userRepository.findUserMedalsByUserId(userProfile.getUserId());

        Map<String, Object> userProfileAndMedals = new HashMap<String, Object>();
        userProfileAndMedals.put("profile", userProfile);
        userProfileAndMedals.put("medals", userMedals);

        return userProfileAndMedals;
    }

    @RequestMapping(value = "/manage/userEdit", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PreAuthorize("hasPermission(#userEditForm, #loginUser)")
    public String userEdit(@ModelAttribute("loginUser") User loginUser, @RequestPart("photo") MultipartFile photo, UserEditForm userEditForm) throws IOException, ParseException {
        User user = userRepository.findUserByName(userEditForm.getOriginName());

        if (loginUser.getUserRole() == UserRole.SUPER_ADMIN) {
            user.setName(userEditForm.getName());
            user.setPassword(StringUtils.isNotEmpty(userEditForm.getPassword()) ? passwordEncoderService.encode(userEditForm.getPassword()) : user.getPassword());
        }

        user.setUserRole(UserRole.valueOf(userEditForm.getRole()));
        user.setEmail(userEditForm.getEmail());
        if (!photo.isEmpty()) {
            user.setPhoto(ImageUtils.compress(photo.getInputStream(), photo_width, photo_height, photo_format, photo_location));
        }
        userRepository.updateUser(user);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        userRepository.updateUserProfileByUserId(
                user.getId(),
                userEditForm.getAlias(),
                Arrays.stream(UserGender.values()).filter(x -> x.name().equals(userEditForm.getGender())).findAny().isPresent() ? UserGender.valueOf(userEditForm.getGender()) : null,
                StringUtils.isNotEmpty(userEditForm.getBirthday()) ? dateFormat.parse(userEditForm.getBirthday()) : null,
                userEditForm.getPhone(),
                userEditForm.getBrief());

        Set<UserMedal> originMedals = userEditForm.getOriginMedals() != null ? new HashSet<UserMedal>(Arrays.stream(userEditForm.getOriginMedals()).map(UserMedal::valueOf).collect(Collectors.toList())) : new HashSet<UserMedal>();
        Set<UserMedal> medals = userEditForm.getMedals() != null ? new HashSet<UserMedal>(Arrays.stream(userEditForm.getMedals()).map(UserMedal::valueOf).collect(Collectors.toList())) : new HashSet<UserMedal>();
        Set<UserMedal> retainMedals = new HashSet<UserMedal>(originMedals);
        retainMedals.retainAll(medals);
        originMedals.removeAll(retainMedals);
        medals.removeAll(retainMedals);

        Set<UserMedal> permitMedals = new HashSet<UserMedal>(Arrays.asList(UserMedal.award(loginUser.getUserRole())));
        originMedals.retainAll(permitMedals);
        medals.retainAll(permitMedals);

        userRepository.updateUserMedalsByUserId(user.getId(), originMedals, medals);

        return "redirect:/user/manage?page=" + userEditForm.getPage();
    }
}
