package org.example.controller;

import org.example.model.User;
import org.example.model.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/page/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "home"; // 對應 /WEB-INF/views/home.jsp
    }



    // 搜尋條件：只有 name（對應 /api/users/search?name=Bob）
    @GetMapping(value = "/search", params = "name")
    public List<User> searchUsersByName(@RequestParam String name) {
        return userRepository.findByName(name);
    }




    // 搜尋條件：name 與 age（對應 /api/users/search?name=Bob&age=25）
    @GetMapping(value = "/search", params = {"name", "age"})
    public List<User> searchByNameAndAge(@RequestParam String name, @RequestParam int age) {
        return userRepository.findByNameAndAge(name, age);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setAge(updatedUser.getAge());
                    user.setGender(updatedUser.getGender());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
