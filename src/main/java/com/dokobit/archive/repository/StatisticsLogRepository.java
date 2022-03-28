package com.dokobit.archive.repository;

import com.dokobit.archive.entity.StatisticsLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StatisticsLogRepository extends JpaRepository<StatisticsLogEntity, Long> {

    Optional<StatisticsLogEntity> findByIpAddressAndRequestUrlAndRequestDate(String ipAddress, String requestUrl, LocalDate requestDate);
}
