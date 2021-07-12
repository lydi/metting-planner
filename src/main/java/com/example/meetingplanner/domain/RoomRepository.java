package com.example.meetingplanner.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query("SELECT r FROM Room r JOIN r.equipments e " +
            "WHERE (e IN :targetGroups)" +
            "AND r.maxAttendees >= :maxAttendees GROUP BY r.id HAVING count(r.id) = :groupsCount")
    List<Room> findByMaxAttendeesGreaterThanAndEquipmentsIn(@Param("maxAttendees") Integer maxAttendees,
                                                            @Param("targetGroups") List<String> equipments,
                                                            @Param("groupsCount") Long groupsCount);
}
