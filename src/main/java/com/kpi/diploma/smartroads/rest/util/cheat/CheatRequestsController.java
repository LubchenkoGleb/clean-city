package com.kpi.diploma.smartroads.rest.util.cheat;

import com.kpi.diploma.smartroads.service.primary.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cheat")
public class CheatRequestsController {

    private final AdminService adminService;

    public CheatRequestsController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/fill-db-with-test-data")
    private ResponseEntity<Boolean> fillDb() {
        adminService.fillDbWithInitData();
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/set-state-of-all-containers")
    private ResponseEntity<Boolean> setStateOfAllContainers(
            @RequestParam boolean pending, @RequestParam boolean fullness) {
        adminService.setStateOfAllContainers(pending, fullness);
        return ResponseEntity.ok(true);
    }
}
