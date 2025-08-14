package com.bst;

import com.bst.model.TreeNode;
import com.bst.model.TreeResult;
import com.bst.service.BinarySearchTreeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BinarySearchTreeServiceTest {

    @Autowired
    private BinarySearchTreeService binarySearchTreeService;

    @Test
    public void testProcessNumbers_RegularBST() {
        // Test creating a regular BST
        List<Integer> numbers = Arrays.asList(5, 3, 7, 2, 4, 6, 8);
        TreeResult result = binarySearchTreeService.processNumbers(numbers, false);

        assertNotNull(result);
        assertNotNull(result.getTree());
        assertEquals(5, result.getTree().getValue()); // Root should be 5 (first inserted)
        assertTrue(binarySearchTreeService.isValidBST(result.getTree()));
        assertEquals(numbers, result.getInputNumbers());
    }

    @Test
    public void testProcessNumbers_BalancedBST() {
        // Test creating a balanced BST
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        TreeResult result = binarySearchTreeService.processNumbers(numbers, true);

        assertNotNull(result);
        assertNotNull(result.getTree());
        assertTrue(binarySearchTreeService.isValidBST(result.getTree()));
        assertTrue(result.isBalanced());

        // Check if the tree is actually balanced
        assertTrue(binarySearchTreeService.isBalanced(result.getTree()));
    }

    @Test
    public void testTreeHeight() {
        // Test tree height calculation
        List<Integer> numbers = Arrays.asList(5, 3, 7, 2, 4);
        TreeResult result = binarySearchTreeService.processNumbers(numbers, false);

        int height = binarySearchTreeService.getTreeHeight(result.getTree());
        assertEquals(3, height); // Expected height for this tree structure
    }

    @Test
    public void testIsValidBST() {
        // Test BST validation
        List<Integer> numbers = Arrays.asList(10, 5, 15, 3, 7, 12, 18);
        TreeResult result = binarySearchTreeService.processNumbers(numbers, false);

        assertTrue(binarySearchTreeService.isValidBST(result.getTree()));
    }

    @Test
    public void testSingleNodeTree() {
        // Test with single node
        List<Integer> numbers = Arrays.asList(42);
        TreeResult result = binarySearchTreeService.processNumbers(numbers, false);

        assertNotNull(result.getTree());
        assertEquals(42, result.getTree().getValue());
        assertNull(result.getTree().getLeft());
        assertNull(result.getTree().getRight());
        assertEquals(1, binarySearchTreeService.getTreeHeight(result.getTree()));
    }

    @Test
    public void testDuplicateNumbers() {
        // Test with duplicate numbers (BST should not insert duplicates)
        List<Integer> numbers = Arrays.asList(5, 3, 5, 7, 3);
        TreeResult result = binarySearchTreeService.processNumbers(numbers, false);

        assertNotNull(result.getTree());
        assertTrue(binarySearchTreeService.isValidBST(result.getTree()));

    }
}