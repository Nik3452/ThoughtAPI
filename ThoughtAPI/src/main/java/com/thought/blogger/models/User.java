package com.thought.blogger.models;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;

@Entity
@EnableAutoConfiguration
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String email = null;

    @Getter
    @Setter
    private String password = null;

    @Setter
    @Getter
    private Date created_at;

}
