package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import lombok.Data;

@Data
public class ManagerDto {

    String firstName;
    String lastName;
    String email;

    public static ManagerDto convert(Manager manager) {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setEmail(manager.getEmail());
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setLastName(manager.getLastName());
        return managerDto;
    }
}
