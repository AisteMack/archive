package com.dokobit.archive.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalendarService {

    public LocalDate getNowForLocalDate() {
        return LocalDate.now();
    }
}
