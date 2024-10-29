package com.ecowaste.recycling.service;

import com.ecowaste.recycling.dto.dashboard.DashboardResponseDto;

public interface DashboardService {
    DashboardResponseDto getDashboardData(Long userId);
}
