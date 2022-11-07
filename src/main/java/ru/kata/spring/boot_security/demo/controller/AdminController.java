package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller

public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/show")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("allUsers", users);
        return "all-users";
    }

    @GetMapping("admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRole());
        return "new";
    }

    @PostMapping("admin/new")
    private String createUser(@RequestParam("role") List<Long> roles, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        User checkUserName = userService.findByUsername(user.getUsername());
        if (bindingResult.hasErrors()) {
            return "new";
        }
        if (checkUserName != null) {
            FieldError error = new FieldError("user", "username", "Username is already in use ");
            bindingResult.addError(error);
            return "new";
        }

        user.setRoles(roleService.getRoleById(roles));
        userService.saveUser(user);
        return "redirect:/admin/show";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("userById", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRole());
        return "edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@RequestParam("role") List<Long> roles, @ModelAttribute("userById") @Valid User user, BindingResult bindingResult) {
        User checkUserName = this.userService.findByUsername(user.getUsername());
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        if (checkUserName != null &&
                !Objects.equals(checkUserName.getId(), user.getId())) {
            FieldError error = new FieldError("user", "username", "Username is already in use ");
            bindingResult.addError(error);
            return "edit";
        }
        user.setRoles(roleService.getRoleById(roles));
        userService.saveUser(user);
        return "redirect:/admin/show";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/show";
    }


}
