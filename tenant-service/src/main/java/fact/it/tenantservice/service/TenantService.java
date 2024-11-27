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
                    .email("dfqsfsqf@dfsqdfsq")
                    .password("kaas")
                    .build();

            tenantRepository.save(tenant);
        }
    }

    public void createTenant(TenantRequest tenantRequest){
        Tenant tenant = Tenant.builder()
                .name(tenantRequest.getName())
                .email(tenantRequest.getEmail())
                .password(tenantRequest.getPassword())
                .description(tenantRequest.getDescription())
                .build();

        tenantRepository.save(tenant);
    }

    public List<TenantResponse> getAllTenants() {
        List<Tenant> products = tenantRepository.findAll();
        return products.stream().map(this::mapToTenantResponse).toList();
    }

    private TenantResponse mapToTenantResponse(Tenant tenant) {
        return TenantResponse.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .email(tenant.getEmail())
                .password(tenant.getPassword())
                .description(tenant.getDescription())
                .build();
    }
}
