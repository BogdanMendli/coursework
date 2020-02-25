package polytech.spbstu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import polytech.spbstu.dto.UserDto;
import polytech.spbstu.entity.UserEntity;
import polytech.spbstu.service.SecurityService;
import polytech.spbstu.service.UserService;
import polytech.spbstu.validator.UserValidator;

@RestController
@RequestMapping("/polyclinic/spbstu/users/")
public class UserRestController {

    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator validator;

    @Autowired
    public UserRestController(UserService userService, SecurityService securityService, UserValidator validator) {
        this.userService = userService;
        this.securityService = securityService;
        this.validator = validator;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.findById(id);
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        final UserDto userDto = UserDto.fromUser(userEntity);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping(value = "registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserEntity());

        return "registration";
    }

    @PostMapping(value = "registration")
    public String registration(@ModelAttribute("userForm") UserEntity userEntityForm,
                               BindingResult bindingResult, Model model) {
        validator.validate(userEntityForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.register(userEntityForm);
        securityService.autoLogin(userEntityForm.getUsername(), userEntityForm.getPassword());
        return "redirect:/welcome";
    }

    @GetMapping(value = "login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username of password is incorrect");
        }

        if (logout != null) {
            model.addAttribute("logout", "Logged out successfully");
        }

        return "login";
    }

    @GetMapping(value = {"", "welocme"})
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping(value = "admin")
    public String admin(Model model) {
        return "admin";
    }
}