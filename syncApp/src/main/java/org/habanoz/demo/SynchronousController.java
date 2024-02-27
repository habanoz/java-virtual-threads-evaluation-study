package org.habanoz.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class SynchronousController {
    public static void main(String[] args) {
        SpringApplication.run(SynchronousController.class, args);
    }
}

@Entity
@Table(name = "users")
class User {

    @Id
    private String id;
    private String name;

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@RestController
class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public int addUser(@RequestBody User user) {
        return jdbcTemplate.update("INSERT INTO users (id, name) VALUES (?, ?)", user.getId(), user.getName());
    }

    @PostMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", User.class, id);
    }
}

