package tehama.society.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tehama.society.model.Branch;
import tehama.society.model.Result;
import tehama.society.model.dto.BranchDTO;
import tehama.society.service.BranchService;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/create")
    public ResponseEntity<Branch> createBranch(@RequestBody BranchDTO branchDTO) {
        Branch newBranch = branchService.createBranch(branchDTO.getBranchName(), branchDTO.getAddress(), branchDTO.getLatitude(), branchDTO.getLongitude());
        return new ResponseEntity<>(newBranch, HttpStatus.CREATED);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> allBranches = branchService.getAllBranches();
        return new ResponseEntity<>(allBranches, HttpStatus.OK);
    }


    @PutMapping("/update/{branchId}")
    public ResponseEntity<Branch> updateBranch(@RequestBody Branch branch, @PathVariable long branchId) {
        Branch updateResult = branchService.updateBranch(branch.getBranchName(), branch.getAddress(), branch.getLatitude(), branch.getLongitude(), branchId);
        return new ResponseEntity<>(updateResult, HttpStatus.OK);
    }


    @GetMapping("/{branchId}")
    public ResponseEntity<Branch> findBranch(@PathVariable long branchId) {
        Branch branch = branchService.findBranch(branchId);
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<?> deleteBranch(@PathVariable long branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
