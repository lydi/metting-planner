package com.example.meetingplanner.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

@Service
public class BookingManager {
    private RoomRepository roomRepository;
    private MeetingRepository meetingRepository;
    private double maxLimit;

    public BookingManager(RoomRepository roomRepository, MeetingRepository meetingRepository, @Value("${maxLimit}") double maxLimit) {
        this.roomRepository = roomRepository;
        this.meetingRepository = meetingRepository;
        this.maxLimit = maxLimit;
    }

    public BookingResponse book(BookingRequest bookingRequest) {
        List<String> equipments = getEquipmentsByMeetingType(bookingRequest.getType());

        List<Room> rooms = roomRepository.findByMaxAttendeesGreaterThanAndEquipmentsIn(bookingRequest.getAttendeesCount(), equipments, Long.valueOf(equipments.size()));

        Optional<Room> availableRoom = rooms.stream()
                .sorted(Comparator.comparingInt(Room::getMaxAttendees))
                .filter(room -> room.getMaxAttendees() * maxLimit >= bookingRequest.getAttendeesCount())
                .filter(isAlreadyBooked(bookingRequest))
                .findFirst();


        if (availableRoom.isPresent()) {
            Room room = availableRoom.get();
            Meeting meeting = new Meeting(room.getName(), bookingRequest.getStartDate().minusHours(1), bookingRequest.getEndDate());
            meetingRepository.save(meeting);
            return new BookingResponse(BookingResponseCode.SUCCEEDED, room.getName());
        } else {
            return new BookingResponse(BookingResponseCode.FAILED, null);
        }


    }

    private Predicate<Room> isAlreadyBooked(BookingRequest bookingRequest) {
        return room -> {
            LocalDateTime startDate = bookingRequest.getStartDate().minusHours(1);
            LocalDateTime endDate = bookingRequest.getEndDate();
            return !meetingRepository.existsMeetingByRoomAndStartDateAndEndDate(room.getName(), startDate, endDate);
        };
    }

    private List<String> getEquipmentsByMeetingType(MeetingType type) {
        switch (type) {
            case RS:
                return asList();
            case RC:
                return asList("tableau", "écran", "pieuvre");
            case VC:
                return asList("écran", "pieuvre", "webcam");
            case SPEC:
                return asList("tableau");
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
