package com.example.meetingplanner.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

@DataJpaTest
class BookingManagerIntegrationTest {

    private RoomRepository roomRepository = mock(RoomRepository.class);
    private MeetingRepository meetingRepository = mock(MeetingRepository.class);
    private double maxLimit = 0.7;

    private BookingManager bookingManager = new BookingManager(roomRepository, meetingRepository, maxLimit);

    @Nested
    class RSMeeting {

        private int attendeesCount = 3;
        private List<String> equipments = asList();
        private LocalDateTime startDate = LocalDateTime.now();
        private LocalDateTime endDate = startDate.plusHours(1);

        @BeforeEach
        void setUp() {
            when(roomRepository.findByMaxAttendeesGreaterThanAndEquipmentsIn(attendeesCount, equipments, Long.valueOf(equipments.size()))).thenReturn(
                    asList(new Room("room1", 3), new Room("room2", 10))
            );
        }

        @Test
        void shouldReturnRoomWithMinMaxAttendees() {

            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate.minusHours(1), endDate)).thenReturn(false);
            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room2", startDate.minusHours(1), endDate)).thenReturn(false);

            BookingRequest bookingRequest = new BookingRequest(attendeesCount, MeetingType.RS, startDate, endDate);

            BookingResponse bookingResponse = bookingManager.book(bookingRequest);
            Assertions.assertThat(bookingResponse.getCode()).isEqualTo(BookingResponseCode.SUCCEEDED);
        }

        @Test
        void shouldOnlyReturnAvailableRoom() {

            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate.minusHours(1), endDate)).thenReturn(true);
            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room2", startDate.minusHours(1), endDate)).thenReturn(false);

            BookingRequest bookingRequest = new BookingRequest(attendeesCount, MeetingType.RS, startDate, endDate);

            BookingResponse bookingResponse = bookingManager.book(bookingRequest);
            Assertions.assertThat(bookingResponse.getCode()).isEqualTo(BookingResponseCode.SUCCEEDED);
        }

        @Test
        void shouldNotReturnAnyRoomIfIsNotAvailable() {

            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate.minusHours(1), endDate)).thenReturn(true);
            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room2", startDate.minusHours(1), endDate)).thenReturn(true);

            BookingRequest bookingRequest = new BookingRequest(attendeesCount, MeetingType.RS, startDate, endDate);

            BookingResponse bookingResponse = bookingManager.book(bookingRequest);
            Assertions.assertThat(bookingResponse.getCode()).isEqualTo(BookingResponseCode.FAILED);
        }

        @Test
        void shouldNotReturnAnyRoomIfAvailabilityIsLessThan70() {


            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate.minusHours(1), endDate)).thenReturn(true);
            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room2", startDate.minusHours(1), endDate)).thenReturn(false);

            BookingRequest bookingRequest = new BookingRequest(8, MeetingType.RS, startDate, endDate);

            BookingResponse bookingResponse = bookingManager.book(bookingRequest);
            Assertions.assertThat(bookingResponse.getCode()).isEqualTo(BookingResponseCode.FAILED);
        }

        @Test
        void shouldNotBookIfRoomWasBusyBeforeOneHour() {

            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room1", startDate.minusHours(1), endDate)).thenReturn(true);
            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room2", startDate.minusHours(1), endDate)).thenReturn(true);

            BookingRequest bookingRequest = new BookingRequest(attendeesCount, MeetingType.RS, startDate, endDate);

            BookingResponse bookingResponse = bookingManager.book(bookingRequest);
            Assertions.assertThat(bookingResponse.getCode()).isEqualTo(BookingResponseCode.FAILED);
        }

        @Test
        void shouldSaveMeeting() {
            when(meetingRepository.existsMeetingByRoomAndStartDateAndEndDate("room2", startDate.minusHours(1), endDate)).thenReturn(false);

            BookingRequest bookingRequest = new BookingRequest(attendeesCount, MeetingType.RS, startDate, endDate);

            BookingResponse bookingResponse = bookingManager.book(bookingRequest);

            Assertions.assertThat(bookingResponse.getCode()).isEqualTo(BookingResponseCode.SUCCEEDED);
            verify(meetingRepository).save(eq(new Meeting("room2", startDate.minusHours(1), endDate)));
        }
    }

}