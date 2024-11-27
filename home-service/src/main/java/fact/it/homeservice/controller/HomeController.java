package fact.it.homeservice.controller;

import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// docker run --name mongo-home -p 27017:27017 -d mongo

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<HomeResponse> getAllTenants() {
        return homeService.getAllHomes();
    }
}
