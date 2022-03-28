package com.dokobit.archive.service

import com.dokobit.archive.entity.StatisticsLogEntity
import com.dokobit.archive.repository.StatisticsLogRepository
import spock.lang.Specification

import java.time.LocalDate

class StatisticsLogServiceTest extends Specification {
    def CURRENT_LOCAL_DATE = LocalDate.of(2022, 01, 01)
    def REQUEST = "/test"
    def IP_ADDRESS = "11.11.11"

    def statisticsLogRepository = Mock(StatisticsLogRepository)
    def calendarService = Mock(CalendarService)

    def apiLogService = new StatisticsLogService(statisticsLogRepository, calendarService)

    def setup() {
        calendarService.getNowForLocalDate() >> CURRENT_LOCAL_DATE
    }

    def "Should count be 1 when it is first request"() {
        given:
        def entity = StatisticsLogEntity.builder()
                .ipAddress(IP_ADDRESS)
                .requestCount(1)
                .requestDate(CURRENT_LOCAL_DATE)
                .requestUrl(REQUEST)
                .id(null)
                .build()
        when:
        apiLogService.logRequest(REQUEST, IP_ADDRESS)
        then:
        1 * statisticsLogRepository.findByIpAddressAndRequestUrlAndRequestDate(IP_ADDRESS, REQUEST, CURRENT_LOCAL_DATE) >> Optional.empty()
        1 * statisticsLogRepository.save(entity)
    }

    def "Should count be increased by 1 when it is not first request"() {
        given:
        def entity = StatisticsLogEntity.builder()
                .ipAddress(IP_ADDRESS)
                .requestCount(1)
                .requestDate(CURRENT_LOCAL_DATE)
                .requestUrl(REQUEST)
                .id(null)
                .build()
        def responseEntity = StatisticsLogEntity.builder()
                .ipAddress(IP_ADDRESS)
                .requestCount(2)
                .requestDate(CURRENT_LOCAL_DATE)
                .requestUrl(REQUEST)
                .id(null)
                .build()
        when:
        apiLogService.logRequest(REQUEST, IP_ADDRESS)
        then:
        1 * statisticsLogRepository.findByIpAddressAndRequestUrlAndRequestDate(IP_ADDRESS, REQUEST, CURRENT_LOCAL_DATE) >> Optional.of(entity)
        1 * statisticsLogRepository.save(responseEntity)
    }

}
