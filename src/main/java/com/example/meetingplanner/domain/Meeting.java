package com.example.meetingplanner.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "MEETING")
public class Meeting {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROOM")
    private String room;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    public Meeting() {
    }

    public Meeting(String room, LocalDateTime startDate, LocalDateTime endDate) {
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(room, meeting.room) && Objects.equals(startDate, meeting.startDate) && Objects.equals(endDate, meeting.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", room='" + room + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
