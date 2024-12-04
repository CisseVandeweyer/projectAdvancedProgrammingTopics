package fact.it.hometenantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeTenantResponse {
    private HomeResponse home;
    private TenantResponse tenant;
    private LocalDate startDate;
    private LocalDate endDate;
}
