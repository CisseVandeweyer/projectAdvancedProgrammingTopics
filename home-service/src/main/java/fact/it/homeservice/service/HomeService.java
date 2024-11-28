package fact.it.homeservice.service;

import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.model.Home;
import fact.it.homeservice.repository.HomeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeService {
    private final HomeRepository homeRepository;

    @PostConstruct
    public void loadData() {
        if (homeRepository.count() <= 0) {
            Home home = Home.builder()
                    .name("Jos")
                    .location("Antwerpen")
                    .build();

            homeRepository.save(home);
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
        return HomeResponse.builder()
                .id(home.getId())
                .name(home.getName())
                .location(home.getLocation())
                .build();
    }
}
