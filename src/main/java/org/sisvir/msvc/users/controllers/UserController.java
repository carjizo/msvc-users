package org.sisvir.msvc.users.controllers;


//import jakarta.validation.Valid;
import org.sisvir.msvc.users.controllers.dto.UserDTO;
import org.sisvir.msvc.users.models.entities.User;
import org.sisvir.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @CrossOrigin
    public ResponseEntity<?> listar() {
        List<UserDTO> userDTOList = userService.findAll()
                .stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .userName(user.getUserName())
                        .build())
                .toList();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailById(@PathVariable Long id) {
         Optional<User> userOptional = userService.findById(id);
         if (userOptional.isPresent()) {
             User user = userOptional.get();
             UserDTO userDTO = UserDTO.builder()
                     .id(user.getId())
                     .userName(user.getUserName())
                     .build();
             return ResponseEntity.ok(userDTO);
         }
         return ResponseEntity.notFound().build();
    }

    // COMENTAR MÉTODO MÁS ADELANTE
    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> detailById(@PathVariable String username) {
        Optional<User> userOptional = userService.findByUserName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .userName(user.getUserName())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    @CrossOrigin
    public ResponseEntity<?> crear(@Valid @RequestBody UserDTO userDTO, BindingResult result) throws URISyntaxException {
        if (!userDTO.getUserName().isEmpty() && userService.findByUserName(userDTO.getUserName()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un usuario con ese username!"));
        }

        if (result.hasErrors()) {
            return validate(result);
        }

//        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.create(User.builder()
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build());

        return ResponseEntity.created(new URI("/users/create")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@Valid @RequestBody UserDTO userDTO, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validate(result);
        }

        Optional<User> userOptional =  userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!userDTO.getUserName().isEmpty() &&
                    !userDTO.getUserName().equalsIgnoreCase(user.getUserName()) &&
                    userService.findByUserName(userDTO.getUserName()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un usuario con ese username!"));
            }
            user.setUserName(userDTO.getUserName());
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.create(user);
            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/authorized")
    public Map<String, Object> authorized(@RequestParam(name = "code") String code) {
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginByUser(@RequestParam(name = "userName") String userName) {
        Optional<User> userOptional = userService.findByUserName(userName);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
