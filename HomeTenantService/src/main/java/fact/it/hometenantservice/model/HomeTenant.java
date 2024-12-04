package fact.it.hometenantservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "homeTenants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeTenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String homeId;
    private Long tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
}
