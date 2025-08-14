package com.bst;

import com.bst.model.TreeResult;
import com.bst.service.BinarySearchTreeService;
import com.bst.model.TreeNode; // <-- adjust if your node class lives elsewhere
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // use Docker MySQL, not H2
@Transactional // rollback DB writes after each test
class BinarySearchTreeServiceTest {

    @Autowired
    private BinarySearchTreeService service;

    // --- helpers ---
    private static List<Integer> list(Integer... n) { return Arrays.asList(n); }

    private static int countValue(TreeNode node, int target) {
        if (node == null) return 0;
        int c = (node.getValue() == target) ? 1 : 0;
        return c + countValue(node.getLeft(), target) + countValue(node.getRight(), target);
    }

    // 1) Regular (unbalanced) BST should be valid and root = first inserted
    @Test
    void buildsRegularBST_andIsValid() {
        List<Integer> nums = list(5, 3, 7, 2, 4, 6, 8);

        TreeResult r = service.processNumbers(nums, /*balanced*/ false);

        assertNotNull(r, "Service should return a result");
        assertNotNull(r.getTree(), "Root node should not be null");
        assertEquals(5, r.getTree().getValue(), "Root should be first inserted value (5)");
        assertTrue(service.isValidBST(r.getTree()), "BST must be valid");
    }

    // 2) Balanced build should have height <= unbalanced build for same data
    @Test
    void balancedHasHeightLessOrEqualToUnbalanced() {
        List<Integer> nums = list(1, 2, 3, 4, 5, 6, 7);

        TreeResult unbalanced = service.processNumbers(nums, false);
        TreeResult balanced   = service.processNumbers(nums, true);

        assertNotNull(unbalanced.getTree());
        assertNotNull(balanced.getTree());
        assertTrue(service.isValidBST(unbalanced.getTree()));
        assertTrue(service.isValidBST(balanced.getTree()));

        int hU = service.getTreeHeight(unbalanced.getTree());
        int hB = service.getTreeHeight(balanced.getTree());
        assertTrue(hB <= hU, "Balanced height (" + hB + ") should be <= unbalanced (" + hU + ")");
    }

    // 3) Duplicates are ignored (adjust if your policy is different)
    @Test
    void duplicatesAreIgnored_policy() {
        List<Integer> nums = list(5, 3, 5, 7, 3);

        TreeResult r = service.processNumbers(nums, false);
        assertNotNull(r.getTree());
        assertTrue(service.isValidBST(r.getTree()));

        // if your service ignores duplicates:
        assertEquals(1, countValue(r.getTree(), 5), "5 should appear once");
        assertEquals(1, countValue(r.getTree(), 3), "3 should appear once");

        // If your policy is to allow duplicates, comment the two lines above and instead assert:
        // assertTrue(countValue(r.getTree(), 5) >= 2);
        // assertTrue(countValue(r.getTree(), 3) >= 2);
    }
}
