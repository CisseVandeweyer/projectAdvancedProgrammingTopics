package fact.it.homeservice.service;

import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.dto.TenantResponse;
import fact.it.homeservice.model.Home;
import fact.it.homeservice.repository.HomeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeService {
    private final HomeRepository homeRepository;
    private final WebClient webClient;

    @PostConstruct
    public void loadData() {
        if (homeRepository.count() <= 0) {
            Home home = Home.builder()
                    .address("Oude Veerlebaan 12")
                    .yearOfConstruction("2012")
                    .type("house")
                    .build();

            Home home1 = Home.builder()
                    .address("Kerkhofweg 18")
                    .yearOfConstruction("2002")
                    .type("house")
                    .build();

            homeRepository.save(home);
            homeRepository.save(home1);
        }
    }

    public List<HomeResponse> getAllHomes() {
        List<Home> homes = homeRepository.findAll();
        return homes.stream().map(this::mapToHomeResponse).toList();
    }

    public HomeResponse getHomeById(String id) {
        Home home = homeRepository.findHomeById(id);
        return mapToHomeResponse(home);
    }

    private HomeResponse mapToHomeResponse(Home home) {
        String tenantServiceBaseUrl = "localhost:8082";

//        TenantResponse[] tenants = webClient.get()
//            .uri("http://" + tenantServiceBaseUrl + "/api/tenant",
//                    uriBuilder -> uriBuilder.queryParam("ids", home.getTenantIds()).build())
//            .retrieve()
//            .bodyToMono(TenantResponse[].class)
//            .block();

//        Optional<TenantResponse> activeTenant = Optional.empty();
//        if (tenants != null) {
//            activeTenant = Arrays.stream(tenants)
//                    .filter(TenantResponse::isActive)
//                    .findFirst();
//        }

        TenantResponse tenant = webClient.get()
                .uri("http://" + tenantServiceBaseUrl + "/api/tenant/home/{id}", home.getId())
                .retrieve()
                .bodyToMono(TenantResponse.class)
                .block();

        return HomeResponse.builder()
                .id(home.getId())
                .address(home.getAddress())
                .yearOfConstruction(home.getYearOfConstruction())
                .type(home.getType())
                .status(tenant != null ? "rented": "not rented")
                .activeTenant(tenant)
                .build();
    }
}
