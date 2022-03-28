package com.dokobit.archive.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "STATISTICS_LOG", uniqueConstraints = @UniqueConstraint(columnNames = {"IP_ADDRESS", "REQUEST_URL", "REQUEST_DATE"}))
public class StatisticsLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;

    @Column(name = "REQUEST_URL", nullable = false)
    private String requestUrl;

    @Column(name = "REQUEST_COUNT", nullable = false)
    private int requestCount;

    @Column(name = "REQUEST_DATE", nullable = false)
    private LocalDate requestDate;
}
