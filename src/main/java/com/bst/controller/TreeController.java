package com.bst.controller;

import com.bst.model.TreeData;
import com.bst.model.TreeNode;
import com.bst.model.TreeResult;
import com.bst.service.BinarySearchTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TreeController {

    private final BinarySearchTreeService binarySearchTreeService;

    public TreeController(BinarySearchTreeService binarySearchTreeService) {
        this.binarySearchTreeService = binarySearchTreeService;
    }

    @GetMapping("/")
    public String home() { return "redirect:/enter-numbers"; }

    @GetMapping("/enter-numbers")
    public String enterNumbers() { return "enter-numbers"; }

    /** Preview BOTH trees without saving  */
    @PostMapping("/api/preview-both")
    @ResponseBody
    public BothTreesResponse apiPreviewBoth(@RequestBody ProcessNumbersRequest request) {
        List<Integer> nums = request.getNumbers();
        TreeNode regular  = binarySearchTreeService.buildTree(nums, false);
        TreeNode balanced = binarySearchTreeService.buildTree(nums, true);
        return new BothTreesResponse(regular, balanced, nums);
    }

    /** Persist exactly ONE result  */
    @PostMapping("/api/process-numbers")
    @ResponseBody
    public TreeResult apiProcessNumbers(@RequestBody ProcessNumbersRequest request) {
        return binarySearchTreeService.processNumbers(request.getNumbers(), request.isBalanced());
    }

    // Optional HTML flow (not used by the JS page)
    @PostMapping("/process-numbers")
    public String processNumbersForm(@RequestParam("numbers") String numbersStr,
                                     @RequestParam(value = "balanced", defaultValue = "false") boolean balanced,
                                     Model model) {
        List<Integer> numbers = java.util.Arrays.stream(numbersStr.split("[,\\s]+"))
                .filter(s -> !s.isBlank())
                .map(s -> Integer.parseInt(s.trim()))
                .toList();
        TreeResult result = binarySearchTreeService.processNumbers(numbers, balanced);
        model.addAttribute("result", result);
        return "tree-result";
    }

    @GetMapping("/previous-trees")
    public String previousTrees(Model model) {
        List<TreeData> trees = binarySearchTreeService.getAllPreviousTrees();
        long balancedCount = trees.stream().filter(TreeData::isBalanced).count();
        long totalCount = trees.size();
        long regularCount = totalCount - balancedCount;

        model.addAttribute("trees", trees);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("balancedCount", balancedCount);
        model.addAttribute("regularCount", regularCount);
        return "previous-trees";
    }

    @GetMapping("/api/previous-trees")
    @ResponseBody
    public List<TreeData> apiPreviousTrees() {
        return binarySearchTreeService.getAllPreviousTrees();
    }

    // --- DTOs ---
    public static class ProcessNumbersRequest {
        private List<Integer> numbers;
        private boolean balanced;
        public List<Integer> getNumbers() { return numbers; }
        public void setNumbers(List<Integer> numbers) { this.numbers = numbers; }
        public boolean isBalanced() { return balanced; }
        public void setBalanced(boolean balanced) { this.balanced = balanced; }
    }

    public static class BothTreesResponse {
        private TreeNode regularTree;
        private TreeNode balancedTree;
        private List<Integer> inputNumbers;

        public BothTreesResponse(TreeNode regularTree, TreeNode balancedTree, List<Integer> inputNumbers) {
            this.regularTree = regularTree;
            this.balancedTree = balancedTree;
            this.inputNumbers = inputNumbers;
        }

        public TreeNode getRegularTree() { return regularTree; }
        public TreeNode getBalancedTree() { return balancedTree; }
        public List<Integer> getInputNumbers() { return inputNumbers; }
    }
}
