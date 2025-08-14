package com.bst.controller;

import com.bst.model.TreeData;
import com.bst.model.TreeResult;
import com.bst.service.BinarySearchTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TreeController {

    private final BinarySearchTreeService binarySearchTreeService;

    // constructor injection (clears the "never assigned" warning)
    public TreeController(BinarySearchTreeService binarySearchTreeService) {
        this.binarySearchTreeService = binarySearchTreeService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/enter-numbers";
    }

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "enter-numbers";
    }

    // JSON API the frontend calls via fetch()
    @PostMapping("/api/process-numbers")
    @ResponseBody
    public TreeResult apiProcessNumbers(@RequestBody ProcessNumbersRequest request) {
        return binarySearchTreeService.processNumbers(request.getNumbers(), request.isBalanced());
    }

    // (optional) keep HTML flow if someone submits without JS
    // You can remove this if you don't need a server-rendered result page.
    @PostMapping("/process-numbers")
    public String processNumbersForm(@RequestParam("numbers") String numbersStr,
                                     @RequestParam(value = "balanced", defaultValue = "false") boolean balanced,
                                     Model model) {
        // Parse "7,3,9,1,5" -> List<Integer>
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

    // Optional JSON endpoint for history
    @GetMapping("/api/previous-trees")
    @ResponseBody
    public List<TreeData> apiPreviousTrees() {
        return binarySearchTreeService.getAllPreviousTrees();
    }

    // --- DTO for the JSON request body ---
    public static class ProcessNumbersRequest {
        private List<Integer> numbers;
        private boolean balanced;

        public List<Integer> getNumbers() { return numbers; }
        public void setNumbers(List<Integer> numbers) { this.numbers = numbers; }

        public boolean isBalanced() { return balanced; }
        public void setBalanced(boolean balanced) { this.balanced = balanced; }
    }
}
