package com.bst.service;

import com.bst.model.TreeNode;
import com.bst.model.TreeData;
import com.bst.model.TreeResult;
import com.bst.repository.TreeDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BinarySearchTreeService {

    @Autowired
    private TreeDataRepository treeDataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /** Build a BST in memory  */
    public TreeNode buildTree(List<Integer> numbers, boolean createBalanced) {
        TreeNode root = null;

        if (createBalanced) {
            // Dedupe + sort so balanced tree also respects "ignore duplicates"
            // (same policy as insert-mode).
            SortedSet<Integer> set = new TreeSet<>(numbers);
            List<Integer> sortedUnique = new ArrayList<>(set);
            root = createBalancedBST(sortedUnique, 0, sortedUnique.size() - 1);
        } else {
            for (Integer number : numbers) {
                root = insert(root, number);
            }
        }
        return root;
    }

    /** Build + persist one result  */
    public TreeResult processNumbers(List<Integer> numbers, boolean createBalanced) {
        TreeNode root = buildTree(numbers, createBalanced);
        try {
            String treeJson = objectMapper.writeValueAsString(root);

            // Save to database (exactly one record per call)
            String inputString = numbers.toString();
            TreeData treeData = new TreeData(inputString, treeJson, createBalanced);
            treeDataRepository.save(treeData);

            return new TreeResult(root, numbers, treeJson, createBalanced);
        } catch (Exception e) {
            throw new RuntimeException("Error processing tree: " + e.getMessage(), e);
        }
    }

    private TreeNode insert(TreeNode root, int value) {
        if (root == null) return new TreeNode(value);
        if (value < root.getValue()) {
            root.setLeft(insert(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(insert(root.getRight(), value));
        }
        // duplicates ignored
        return root;
    }

    private TreeNode createBalancedBST(List<Integer> sortedNumbers, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(sortedNumbers.get(mid));
        node.setLeft(createBalancedBST(sortedNumbers, start, mid - 1));
        node.setRight(createBalancedBST(sortedNumbers, mid + 1, end));
        return node;
    }

    public List<TreeData> getAllPreviousTrees() {
        return treeDataRepository.findAllOrderByCreatedAtDesc();
    }

    public List<TreeData> getBalancedTrees() {
        return treeDataRepository.findBalancedTreesOrderByCreatedAtDesc();
    }

    // Helpers used by tests / UI info
    public boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValidBSTHelper(TreeNode node, int min, int max) {
        if (node == null) return true;
        if (node.getValue() <= min || node.getValue() >= max) return false;
        return isValidBSTHelper(node.getLeft(), min, node.getValue()) &&
                isValidBSTHelper(node.getRight(), node.getValue(), max);
    }

    public int getTreeHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(getTreeHeight(root.getLeft()), getTreeHeight(root.getRight()));
    }

    public boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private int checkBalance(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = checkBalance(root.getLeft());
        if (leftHeight == -1) return -1;
        int rightHeight = checkBalance(root.getRight());
        if (rightHeight == -1) return -1;
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
