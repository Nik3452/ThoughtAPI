package com.thought.blogger.models;


import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;

@Entity
@EnableAutoConfiguration
@Table(name = "Blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String owner;

    @Setter
    @Getter
    private String text;

    @Setter
    @Getter
    private String category;

    @Setter
    @Getter
    private Date created_at;

}
