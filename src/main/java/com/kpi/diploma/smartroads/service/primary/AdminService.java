package com.kpi.diploma.smartroads.service.primary;

public interface AdminService {

    void fillDbWithInitData();

    void setStateOfAllContainers(boolean pending, boolean fullness);
}
