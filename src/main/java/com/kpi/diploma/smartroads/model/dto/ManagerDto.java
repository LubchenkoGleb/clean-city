package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Manager;
import lombok.Data;

@Data
public class ManagerDto extends UserDto {
    String firstName;
    String lastName;

    ManagerDto convert(Manager manager) {
        ManagerDto managerDto = (ManagerDto) UserDto.convert(manager);
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setLastName(manager.getLastName());
        return managerDto;
    }
}
