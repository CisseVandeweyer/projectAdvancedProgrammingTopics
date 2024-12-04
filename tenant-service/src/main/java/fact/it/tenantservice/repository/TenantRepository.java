package fact.it.tenantservice.repository;

import fact.it.tenantservice.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    @Query("SELECT t FROM Tenant t WHERE t.id IN :ids")
    List<Tenant> findAllByIds(@Param("ids") List<Long> ids);

    @Query("SELECT t FROM Tenant t WHERE t.id = :id")
    Tenant findTenantById(String id);

}
