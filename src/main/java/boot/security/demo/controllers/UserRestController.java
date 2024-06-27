package boot.security.demo.controllers;

import boot.security.demo.model.Role;
import boot.security.demo.model.User;
import boot.security.demo.service.RoleService;
import boot.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private RoleService roleService;

    // Получение пользователя по ID
    @GetMapping("/user/{userId}")
    public User findById(@PathVariable int userId) {
        return userService.getById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }

    // Создание или обновление пользователя
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> update(@PathVariable int userId, @RequestBody Map<String, Object> updates) {
        User updatedUser = userService.editUserWithRoles(userId, updates);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAll(); // Получение списка ролей из сервиса
        return ResponseEntity.ok(roles);
    }



//    @DeleteMapping("/{userId}")
//    public void delete(@PathVariable int userId) {
//        userService.delete(userId);
//    }

    // Дополнительные методы можно добавить здесь
}
