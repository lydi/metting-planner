package com.example.meetingplanner.domain;

public class BookingResponse {
    private BookingResponseCode code;
    private String room;

    public BookingResponse(BookingResponseCode code, String room) {
        this.code = code;
        this.room = room;
    }

    public BookingResponseCode getCode() {
        return code;
    }

    public void setCode(BookingResponseCode code) {
        this.code = code;
    }

    public String getRoom() {
        return room;
    }
}

enum BookingResponseCode {
    FAILED, SUCCEEDED
}