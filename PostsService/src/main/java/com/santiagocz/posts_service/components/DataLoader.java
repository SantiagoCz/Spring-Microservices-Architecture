package com.santiagocz.posts_service.components;

import com.santiagocz.posts_service.services.PostService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataLoader {

    @Autowired
    private PostService postService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void loadData() {
        executeSql();
    }

    public void executeSql() {
        // Check if data has already been loaded
        if (!postService.getAllPosts().isEmpty()) {
            System.out.println("Data has already been loaded");
            return;
        }

        // Load and execute the SQL script from the file
        Resource resource = resourceLoader.getResource("classpath:posts.sql");

        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
            System.out.println("Data from posts.sql has been loaded successfully");
        } catch (SQLException e) {
            System.err.println("Error executing the SQL script: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
