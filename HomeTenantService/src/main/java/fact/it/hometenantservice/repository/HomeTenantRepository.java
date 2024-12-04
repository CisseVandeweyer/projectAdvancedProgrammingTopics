package fact.it.hometenantservice.repository;

import fact.it.hometenantservice.model.HomeTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeTenantRepository extends JpaRepository<HomeTenant, Long> {
    @Query("SELECT ht FROM HomeTenant ht WHERE ht.homeId = :homeId")
    List<HomeTenant> findHomeTenantsByHomeId(@Param("homeId") String homeId);

//    @Query("SELECT ht FROM HomeTenant ht WHERE ht.homeId = :homeId AND (ht.startDate <= CURRENT_DATE AND (ht.endDate IS NULL OR ht.endDate >= CURRENT_DATE))")
//    HomeTenant findCurrentTenantByHomeId(@Param("homeId") String homeId);
}
