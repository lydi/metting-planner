package com.example.meetingplanner.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MeetingRepositoryIntegrationTest {


    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void shouldReturnFalse() {
        LocalDateTime startDate = LocalDateTime.now();
        Meeting meeting1 = new Meeting("room1", startDate, LocalDateTime.now().plusHours(1));
        meetingRepository.save(meeting1);

        boolean isBooked = meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate.plusHours(2), startDate.plusHours(3));

        assertThat(isBooked).isFalse();

    }

    @Test
    void shouldReturnTrue() {
        LocalDateTime startDate = LocalDateTime.now();
        Meeting meeting1 = new Meeting("room1", startDate, LocalDateTime.now().plusHours(1));
        meetingRepository.save(meeting1);

        boolean isBooked = meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate, LocalDateTime.now().plusHours(1));

        assertThat(isBooked).isTrue();

    }
}