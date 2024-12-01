package fact.it.tenantservice.service;

import fact.it.tenantservice.dto.TenantRequest;
import fact.it.tenantservice.dto.TenantResponse;
import fact.it.tenantservice.model.Tenant;
import fact.it.tenantservice.repository.TenantRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantService {
    private final TenantRepository tenantRepository;

    @PostConstruct
    public void loadData() {
        if (tenantRepository.count() <= 0) {
            Tenant tenant = Tenant.builder()
                    .name("Jos")
                    .description("fffssqfssqsqfsq")
                    .email("jos@jos.com")
                    .homeId("6749d32a57152b1518eb50e3")
                    .build();

            Tenant tenant1 = Tenant.builder()
                    .name("Marie")
                    .description("hello")
                    .email("marie@dfqfqs.com")
                    .homeId("67499f56d8cd7f17bc5ab6ec")
                    .build();

            tenantRepository.save(tenant);
            tenantRepository.save(tenant1);
        }
    }

    public void createTenant(TenantRequest tenantRequest){
        Tenant tenant = Tenant.builder()
                .name(tenantRequest.getName())
                .email(tenantRequest.getEmail())
                .description(tenantRequest.getDescription())
                .build();

        tenantRepository.save(tenant);
    }

    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream().map(this::mapToTenantResponse).toList();
    }

    public List<TenantResponse> getTenantsByIds(List<Long> ids) {
        List<Tenant> tenants = tenantRepository.findAllByIds(ids);
        return tenants.stream().map(this::mapToTenantResponse).toList();
    }

    public TenantResponse getTenantByHomeId(String homeId) {
        Optional<Tenant> tenant = tenantRepository.findAllByHomeId(homeId).stream().findFirst();
        return tenant.map(this::mapToTenantResponse).orElse(null);
    }

    private TenantResponse mapToTenantResponse(Tenant tenant) {
        return TenantResponse.builder()
                .name(tenant.getName())
                .email(tenant.getEmail())
                .description(tenant.getDescription())
                .build();
    }
}
