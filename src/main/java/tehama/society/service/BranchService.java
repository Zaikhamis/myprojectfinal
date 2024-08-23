package tehama.society.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tehama.society.model.Branch;
import tehama.society.model.Payment;
import tehama.society.repository.BranchRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {
    private final BranchRepository branchRepository;


    public Branch createBranch(String branchName, String address, String latitude, String longitude) {
        Branch branch = new Branch();
        branch.setBranchName(branchName);
        branch.setAddress(address);
        branch.setLatitude(latitude);
        branch.setLongitude(longitude);
        branchRepository.save(branch);
        return branch;
    }


    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }


    public Branch updateBranch(String branchName, String address, String latitude, String longitude, long branchId) {
        Branch updateBranch = findBranch(branchId);
        updateBranch.setBranchName(branchName);
        updateBranch.setAddress(address);
        updateBranch.setLatitude(latitude);
        updateBranch.setLongitude(longitude);
        branchRepository.save(updateBranch);
        return updateBranch;
    }


    public Branch findBranch(long id) {
        Branch branch = branchRepository.findByBranchId(id);
        if (branch == null) {
            throw new RuntimeException("Branch not found");
        }

        return branch;
    }


    public void deleteBranch(long id) {
        Branch branch = findBranch(id);
        branchRepository.deleteById(branch.getBranchId());
    }
}
