package com.example.meetingplanner.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ROOM")
public class Room implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;

    @Column(name = "MAX_ATTENDEES")
    private Integer maxAttendees;

    public Room() {
    }

    @ElementCollection
    @CollectionTable(
            name = "ROOM_EQUIPMENTS",
            joinColumns = @JoinColumn(name = "NAME", referencedColumnName = "NAME")
    )
    @Column(name = "EQUIPMENT")
    private List<String> equipments = new ArrayList<>();


    public Room(String name, Integer maxAttendees) {
        this.name = name;
        this.maxAttendees = maxAttendees;
    }

    public Room(String name, Integer maxAttendees, List<String> equipments) {
        this(name, maxAttendees);
        this.equipments = equipments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(Integer maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAttendees=" + maxAttendees +
                ", equipments=" + equipments +
                '}';
    }
}
