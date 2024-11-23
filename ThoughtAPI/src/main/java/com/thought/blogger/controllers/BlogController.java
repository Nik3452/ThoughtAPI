package com.thought.blogger.controllers;

import com.thought.blogger.models.Blog;
import com.thought.blogger.repositories.BlogRepository;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63344")
@RestController
@RequestMapping("/api")
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("/blogs")
    @Description("Gets All Personal Blogs")
    public ResponseEntity<List<Blog>> getAll() {
        try {
            List<Blog> items = new ArrayList<Blog>(blogRepository.findAll());
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/blogs/{id}")
    @Description("Gets All Personal Blogs with specific ID")
    public ResponseEntity<Optional<Blog>> getAllByID(@PathVariable Integer id) {
        return new ResponseEntity<>(blogRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/blogs/{category}")
    @Description("Gets All Personal Blogs with specific Category")
    public ResponseEntity<Optional<Blog>> getAllByCategory(@PathVariable String category) {
        return new ResponseEntity<>(blogRepository.findByCategory(category), HttpStatus.OK);
    }

    @PostMapping("/blogs")
    @Description("Add a new Personal Blog")
    public ResponseEntity<Blog> create(@RequestBody Blog blog) {
        try {
            Blog _blog = new Blog();
            _blog.setTitle(blog.getTitle());
            _blog.setOwner(blog.getOwner());
            _blog.setText(blog.getText());
            _blog.setCategory(blog.getCategory());
            _blog.setCreated_at(new Date());
            blogRepository.save(_blog);
            return new ResponseEntity<>(_blog, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/blogs/{id}")
    @Description("Update a Personal Blog")
    public ResponseEntity<Blog> update(@PathVariable("id") Integer id, @RequestBody Blog blog) {
        Optional<Blog> blogData = blogRepository.findById(id);
        if (blogData.isPresent()) {
            Blog _blog = blogData.get();
            _blog.setTitle(blog.getTitle());
            _blog.setOwner(blog.getOwner());
            _blog.setText(blog.getText());
            return new ResponseEntity<>(blogRepository.save(_blog), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/blogs/{id}")
    @Description("Delete a Personal Blog")
    public ResponseEntity<Blog> delete(@PathVariable("id") Integer id) {
        try {
            blogRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
