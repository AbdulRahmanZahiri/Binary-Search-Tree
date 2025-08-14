package com.bst.model;

import java.util.List;

public class TreeResult {
    private TreeNode tree;
    private List<Integer> inputNumbers;
    private String treeJson;
    private boolean isBalanced;

    public TreeResult() {}

    public TreeResult(TreeNode tree, List<Integer> inputNumbers, String treeJson, boolean isBalanced) {
        this.tree = tree;
        this.inputNumbers = inputNumbers;
        this.treeJson = treeJson;
        this.isBalanced = isBalanced;
    }

    // Getters and Setters
    public TreeNode getTree() {
        return tree;
    }

    public void setTree(TreeNode tree) {
        this.tree = tree;
    }

    public List<Integer> getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(List<Integer> inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public String getTreeJson() {
        return treeJson;
    }

    public void setTreeJson(String treeJson) {
        this.treeJson = treeJson;
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
    }
}