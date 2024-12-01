package fact.it.homeservice.service;

import fact.it.homeservice.dto.HomeRequest;
import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.dto.PaymentResponse;
import fact.it.homeservice.dto.TenantResponse;
import fact.it.homeservice.model.Home;
import fact.it.homeservice.repository.HomeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class HomeService {
    private final HomeRepository homeRepository;
    private final WebClient webClient;

    @Value("${tenantService.baseurl}")
    private String tenantServiceBaseUrl;

    @Value("${maintenanceService.baseurl}")
    private String maintenanceServiceBaseUrl;


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

    public void addHome(HomeRequest homeRequest){
        Home home = Home.builder()
                .type(homeRequest.getType())
                .yearOfConstruction(homeRequest.getYearOfConstruction())
                .address(homeRequest.getAddress())
                .build();

        homeRepository.save(home);
    }

    public void updateHome(String id, HomeRequest homeRequest) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            home.setAddress(homeRequest.getAddress());
            home.setType(home.getType());
            home.setYearOfConstruction(homeRequest.getYearOfConstruction());

            homeRepository.save(home);
        }
    }

    public void deleteHome(String id) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            homeRepository.delete(home);
        }
    }

    public List<PaymentResponse> getPayments(String id) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            // Request naar payment service
        }
        return new ArrayList<>();
    }

    private HomeResponse mapToHomeResponse(Home home) {
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
                .tenant(tenant)
                .build();
    }
}
