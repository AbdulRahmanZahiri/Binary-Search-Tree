package com.bst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BstApplication {

    public static void main(String[] args) {
        // Start Spring and grab the Environment so we can read server.port
        ConfigurableApplicationContext ctx = SpringApplication.run(BstApplication.class, args);
        Environment env = ctx.getEnvironment();

        String port = env.getProperty("server.port", "8080");
        String base = "http://localhost:" + port;

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" Binary Search Tree Application Started Successfully!");
        System.out.println("=".repeat(60));
        System.out.println(" Web Interface: " + base);
        System.out.println(" Main Page: " + base + "/enter-numbers");
        System.out.println(" Previous Trees: " + base + "/previous-trees");
        System.out.println("=".repeat(60) + "\n");
    }
}
