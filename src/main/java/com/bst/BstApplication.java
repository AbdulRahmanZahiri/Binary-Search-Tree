package com.bst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BstApplication {

    public static void main(String[] args) {
        SpringApplication.run(BstApplication.class, args);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🌳 Binary Search Tree Application Started Successfully!");
        System.out.println("=".repeat(60));
        System.out.println("📱 Web Interface: http://localhost:8081");
        System.out.println("🔗 Main Page: http://localhost:8081/enter-numbers");
        System.out.println("📚 Previous Trees: http://localhost:8081/previous-trees");
        System.out.println("🗄️ H2 Database Console: http://localhost:8081/h2-console");
        System.out.println("=".repeat(60) + "\n");
    }
}