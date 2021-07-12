package com.example.meetingplanner.domain;

import java.time.LocalDateTime;

public class BookingRequest {

    private Integer attendeesCount;
    private MeetingType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public BookingRequest() {
    }

    public BookingRequest(Integer attendeesCount, MeetingType type, LocalDateTime startDate, LocalDateTime endDate) {
        this.attendeesCount = attendeesCount;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getAttendeesCount() {
        return attendeesCount;
    }

    public void setAttendeesCount(Integer attendeesCount) {
        this.attendeesCount = attendeesCount;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
