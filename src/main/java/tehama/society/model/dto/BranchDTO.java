package tehama.society.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private String branchName;
    private String address;
    private String latitude;
    private String longitude;
}
