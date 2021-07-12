package com.example.meetingplanner.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface MeetingRepository extends CrudRepository<Meeting,Long> {
    boolean existsMeetingByRoomAndStartDateAndEndDate(String room, LocalDateTime startDate, LocalDateTime endDate);
}
