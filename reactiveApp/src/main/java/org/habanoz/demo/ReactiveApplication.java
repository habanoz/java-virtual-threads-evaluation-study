package org.habanoz.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@SpringBootApplication
public class ReactiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }
}

record Users(@Id String id, String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users customer)) {
            return false;
        }
        return Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

@RestController
class ReactiveUserController {
    @Autowired
    private DatabaseClient databaseClient;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Long> addUser(@RequestBody Users user) {
        return databaseClient.sql("INSERT INTO users (id, name) VALUES (:id, :name)")
                .bind("id", user.id())
                .bind("name", user.name())
                .fetch()
                .rowsUpdated();
    }

    @PostMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Users> getUser(@PathVariable String id) {
        return this.databaseClient.sql("SELECT * FROM users WHERE id = :id")
                .bind("id", id)
                .map((row, metadata) -> new Users(row.get("id", String.class), row.get("name", String.class)))
                .one();
    }
}
