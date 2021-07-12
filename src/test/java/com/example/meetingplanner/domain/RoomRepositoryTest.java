package com.example.meetingplanner.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void shouldReturnRoom() {
        Room room1 = new Room("room1", 12, asList("eq1"));
        roomRepository.save(room1);
        List<Room> rooms = roomRepository.findByMaxAttendeesGreaterThanAndEquipmentsIn(4, asList("eq1"), 1L);
        assertThat(rooms).containsExactly(room1);
    }

    @Test
    void shouldNotReturnRoomIfAllEquipmentsNotIncluded() {
        Room room1 = new Room("room1", 12, asList("eq1"));
        roomRepository.save(room1);
        List<Room> rooms = roomRepository.findByMaxAttendeesGreaterThanAndEquipmentsIn(20, asList("eq1"), 1L);
        assertThat(rooms).isEmpty();
    }

    @Test
    void shouldNotReturnRoomIfAllEquipmenetsNotIncluded() {
        Room room1 = new Room("room1", 12, asList("eq1"));
        roomRepository.save(room1);
        List<Room> rooms = roomRepository.findByMaxAttendeesGreaterThanAndEquipmentsIn(2, asList("eq1", "eq2"), 2L);
        assertThat(rooms).isEmpty();
    }

}