package fact.it.hometenantservice.service;

import fact.it.hometenantservice.dto.HomeResponse;
import fact.it.hometenantservice.dto.HomeTenantResponse;
import fact.it.hometenantservice.dto.TenantResponse;
import fact.it.hometenantservice.model.HomeTenant;
import fact.it.hometenantservice.repository.HomeTenantRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeTenantService {
    private final HomeTenantRepository homeTenantRepository;
    private final WebClient webClient;

    @Value("${tenantService.baseurl}")
    private String tenantServiceBaseUrl;

    @Value("${homeService.baseurl}")
    private String homeServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if (homeTenantRepository.count() <= 0) {
            HomeTenant homeTenant = HomeTenant.builder()
                    .homeId("675045ba3c356e111dff4e13")
                    .tenantId(1L)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .build();

            HomeTenant homeTenant1 = HomeTenant.builder()
                    .homeId("675045ba3c356e111dff4e14")
                    .tenantId(2L)
                    .startDate(LocalDate.now().minusDays(7))
                    .endDate(LocalDate.now().minusDays(2))
                    .build();

            HomeTenant homeTenant2 = HomeTenant.builder()
                    .homeId("675045ba3c356e111dff4e14")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(1))
                    .build();

            homeTenantRepository.save(homeTenant);
            homeTenantRepository.save(homeTenant1);
            homeTenantRepository.save(homeTenant2);
        }
    }

    public List<HomeTenantResponse> getAll() {
        List<HomeTenant> tenants = homeTenantRepository.findAll();
        return tenants.stream().map(this::mapToHomeTenantResponse).toList();
    }

    public List<HomeTenantResponse> getHomeTenantsByHomeId(String id) {
        List<HomeTenant> homeTenants = homeTenantRepository.findHomeTenantsByHomeId(id);
        return homeTenants.stream().map(this::mapToHomeTenantResponse).toList();
    }

//    public HomeTenantResponse getCurrentHomeTenantByHomeId(String id) {
//        HomeTenant currentHomeTenant = homeTenantRepository.findCurrentTenantByHomeId(id);
//        if (currentHomeTenant != null) {
//            return mapToHomeTenantResponse(currentHomeTenant);
//        }
//
//        return null;
//    }

    private HomeTenantResponse mapToHomeTenantResponse(HomeTenant homeTenant) {
        HomeResponse home = webClient.get()
                .uri("http://" + homeServiceBaseUrl + "/api/home/{id}", homeTenant.getHomeId())
                .retrieve()
                .bodyToMono(HomeResponse.class)
                .block();

        TenantResponse tenant = webClient.get()
                .uri("http://" + tenantServiceBaseUrl + "/api/tenant/{id}", homeTenant.getTenantId())
                .retrieve()
                .bodyToMono(TenantResponse.class)
                .block();

        return HomeTenantResponse.builder()
//                .home(home)
                .tenant(tenant)
                .startDate(homeTenant.getStartDate())
                .endDate(homeTenant.getEndDate())
                .build();
    }
}
