package fact.it.contractservice.service;

import fact.it.contractservice.dto.ContractResponse;
import fact.it.contractservice.dto.TenantResponse;
import fact.it.contractservice.model.Contract;
import fact.it.contractservice.repository.ContractRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {
    private final ContractRepository contractRepository;
    private final WebClient webClient;

    @Value("${tenantService.baseurl}")
    private String tenantServiceBaseUrl;

    @Value("${homeService.baseurl}")
    private String homeServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if (contractRepository.count() <= 0) {
            Contract contract = Contract.builder()
                    .homeId("6749e4c84045c20aaf6cd70d")
                    .tenantId(1L)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .build();

            Contract contract1 = Contract.builder()
                    .homeId("6749e4c84045c20aaf6cd70e")
                    .tenantId(2L)
                    .startDate(LocalDate.now().minusDays(7))
                    .endDate(LocalDate.now().minusDays(2))
                    .build();

            Contract contract2 = Contract.builder()
                    .homeId("6749e4c84045c20aaf6cd70e")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(1))
                    .build();

            contractRepository.save(contract);
            contractRepository.save(contract1);
            contractRepository.save(contract2);
        }
    }

    public List<ContractResponse> getAll() {
        List<Contract> tenants = contractRepository.findAll();
        return tenants.stream().map(this::mapToHomeTenantResponse).toList();
    }

    public List<ContractResponse> getHomeTenantsByHomeId(String id) {
        List<Contract> contracts = contractRepository.findHomeTenantsByHomeId(id);
        return contracts.stream().map(this::mapToHomeTenantResponse).toList();
    }

//    public HomeTenantResponse getCurrentHomeTenantByHomeId(String id) {
//        HomeTenant currentHomeTenant = homeTenantRepository.findCurrentTenantByHomeId(id);
//        if (currentHomeTenant != null) {
//            return mapToHomeTenantResponse(currentHomeTenant);
//        }
//
//        return null;
//    }

    private ContractResponse mapToHomeTenantResponse(Contract contract) {
//        HomeResponse home = webClient.get()
//                .uri("http://" + homeServiceBaseUrl + "/api/home/{id}", contract.getHomeId())
//                .retrieve()
//                .bodyToMono(HomeResponse.class)
//                .block();

        TenantResponse tenant = webClient.get()
                .uri("http://" + tenantServiceBaseUrl + "/api/tenant/{id}", contract.getTenantId())
                .retrieve()
                .bodyToMono(TenantResponse.class)
                .block();

        return ContractResponse.builder()
//                .home(home)
                .tenant(tenant)
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .build();
    }
}
