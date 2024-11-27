package fact.it.tenantservice.repository;

import fact.it.tenantservice.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
