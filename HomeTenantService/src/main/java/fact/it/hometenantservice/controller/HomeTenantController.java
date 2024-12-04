package fact.it.hometenantservice.controller;


import fact.it.hometenantservice.dto.HomeTenantResponse;
import fact.it.hometenantservice.service.HomeTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// docker run --name mysql-home-tenant -p 3334:3306 -e MYSQL_ROOT_PASSWORD=abc123 -d mysql

@RestController
@RequestMapping("/api/homeTenant")
@RequiredArgsConstructor
public class HomeTenantController {
    private final HomeTenantService homeTenantService;

//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public List<HomeTenantResponse> getAllHomeTenants() {
//        return homeTenantService.getAll();
//    }

    @GetMapping("/home/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<HomeTenantResponse> getHomeTenantsByUserId(@PathVariable String id) {
        return homeTenantService.getHomeTenantsByHomeId(id);
    }

//    @GetMapping("/home/{id}/current")
//    @ResponseStatus(HttpStatus.OK)
//    public HomeTenantResponse getCurrentHomeTenantsByUserId(@PathVariable String id) {
//        return homeTenantService.getCurrentHomeTenantByHomeId(id);
//    }
}
