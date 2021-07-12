package com.example.meetingplanner;

import com.example.meetingplanner.domain.Room;
import com.example.meetingplanner.domain.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class MeetingPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingPlannerApplication.class, args);
    }

    @Component
    class InitData implements CommandLineRunner{

        private RoomRepository roomRepository;

        public InitData(RoomRepository roomRepository) {
            this.roomRepository = roomRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            roomRepository.save(new Room("room1", 10, Arrays.asList("pieuvre", "écran","webcam")));
        }
    }

}
