package fact.it.homeservice.controller;

import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// docker run --name mongo-home -p 27017:27017 -d mongo

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<HomeResponse> getAllHomes() {
        return homeService.getAllHomes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HomeResponse getHomeById(@PathVariable String id) {
        return homeService.getHomeById(id);
    }
}
