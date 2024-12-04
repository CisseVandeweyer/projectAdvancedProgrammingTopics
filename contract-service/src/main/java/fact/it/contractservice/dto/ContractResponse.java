package fact.it.contractservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponse {
    private HomeResponse home;
    private TenantResponse tenant;
    private LocalDate startDate;
    private LocalDate endDate;
}
