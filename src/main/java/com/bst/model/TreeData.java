package com.bst.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tree_data")
public class TreeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_numbers", columnDefinition = "TEXT")
    private String inputNumbers;

    @Column(name = "tree_json", columnDefinition = "TEXT")
    private String treeJson;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_balanced")
    private boolean isBalanced;

    public TreeData() {
        this.createdAt = LocalDateTime.now();
    }

    public TreeData(String inputNumbers, String treeJson, boolean isBalanced) {
        this.inputNumbers = inputNumbers;
        this.treeJson = treeJson;
        this.isBalanced = isBalanced;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(String inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public String getTreeJson() {
        return treeJson;
    }

    public void setTreeJson(String treeJson) {
        this.treeJson = treeJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
    }
}