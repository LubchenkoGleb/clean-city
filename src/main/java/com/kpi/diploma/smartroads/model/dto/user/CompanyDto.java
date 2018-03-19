package com.kpi.diploma.smartroads.model.dto.user;

import com.kpi.diploma.smartroads.service.util.ConversionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CompanyDto extends UserDto {

    private String title;

    public static CompanyDto convert(Object object) {
        log.info("'convert' invoked with params'{}'", object);

        CompanyDto companyDto = ConversionService.convertToObject(object, CompanyDto.class);
        log.info("'companyDto={}'", companyDto);

        return companyDto;
    }
}
