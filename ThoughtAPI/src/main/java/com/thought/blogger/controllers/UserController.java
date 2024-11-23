package com.thought.blogger.controllers;

import com.thought.blogger.models.Blog;
import com.thought.blogger.models.User;
import com.thought.blogger.repositories.BlogRepository;
import com.thought.blogger.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63344")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Value("${auth.password.workload:10}")
    private Integer workload;

    @GetMapping("/users")
    @Description("Gets All Users")
    public ResponseEntity<List<User>> getAll() {
        try {
            List<User> items = new ArrayList<User>(userRepository.findAll());
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/users/{id}")
    @Description("Gets All Users with specific ID")
    public ResponseEntity<Optional<User>> getAllByID(@PathVariable Integer id) {
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
    }
    @GetMapping("/users/{email}")
    @Description("Gets All Users with email")
    public ResponseEntity<Optional<User>> getAllByEmail(@PathVariable String email) {
            return new ResponseEntity<>(userRepository.findByEmail(email), HttpStatus.OK);
    }
    @PostMapping("/users/login")
    @Description("Verify User Login")
    public ResponseEntity<Boolean> verifyUserLogin(@RequestBody User loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean isPasswordMatch = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
                return new ResponseEntity<>(isPasswordMatch, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/users")
    @Description("Add a new User")
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            User _user = new User();
            _user.setEmail(user.getEmail());
            _user.setPassword(hashedPassword(user.getPassword()));
            _user.setCreated_at(new Date());
            userRepository.save(_user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private String hashedPassword(String plain) {
        return (BCrypt.hashpw(plain, BCrypt.gensalt(this.workload)));
    }
}
