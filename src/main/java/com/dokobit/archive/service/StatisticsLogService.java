package com.dokobit.archive.service;

import com.dokobit.archive.entity.StatisticsLogEntity;
import com.dokobit.archive.repository.StatisticsLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsLogService {

    private final StatisticsLogRepository statisticsLogRepository;
    private final CalendarService calendarService;

    public void logRequest(String request, String ip) {
        var entity = statisticsLogRepository.findByIpAddressAndRequestUrlAndRequestDate(
                ip,
                request,
                calendarService.getNowForLocalDate())
                .orElse(buildLogEntity(request, ip));
        entity.setRequestCount(entity.getRequestCount() + 1);
        saveRequest(entity);
    }

    private void saveRequest(StatisticsLogEntity entity) {
        statisticsLogRepository.save(entity);
    }

    private StatisticsLogEntity buildLogEntity(String request, String ip) {
        return StatisticsLogEntity.builder()
                .ipAddress(ip)
                .requestUrl(request)
                .requestDate(calendarService.getNowForLocalDate())
                .requestCount(0)
                .build();
    }
}
