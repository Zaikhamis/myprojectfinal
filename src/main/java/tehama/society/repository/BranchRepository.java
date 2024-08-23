package tehama.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tehama.society.model.Branch;
import tehama.society.model.Result;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findByBranchId(long branchId);
}
