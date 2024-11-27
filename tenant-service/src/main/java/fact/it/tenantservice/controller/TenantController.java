package fact.it.tenantservice.controller;


import fact.it.tenantservice.dto.TenantRequest;
import fact.it.tenantservice.dto.TenantResponse;
import fact.it.tenantservice.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* docker run --name mysql-tenant -p 3333:3306 -e MYSQL_ROOT_PASSWORD=abc123 -d mysql */

@RestController
@RequestMapping("/api/tenant")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createProduct
            (@RequestBody TenantRequest tenantRequest) {
        tenantService.createTenant(tenantRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TenantResponse> getAllTenants() {
        return tenantService.getAllTenants();
    }
}