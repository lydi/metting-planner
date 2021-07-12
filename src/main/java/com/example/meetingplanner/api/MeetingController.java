package com.example.meetingplanner.api;


import com.example.meetingplanner.domain.BookingManager;
import com.example.meetingplanner.domain.BookingRequest;
import com.example.meetingplanner.domain.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {

    private BookingManager bookingManager;

    @Autowired
    public MeetingController(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    @PostMapping("/api/meetings")
    BookingResponse book(@RequestBody BookingRequest bookingRequest) {
        return bookingManager.book(bookingRequest);
    }

}
