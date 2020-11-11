package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 */
@JsonAutoDetect
public class FlightMission extends AbstractBaseEntity {
    // todo
    @JsonIgnore
    private static long counter = 0;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long missionDistance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew; //based on ship capacity ??
    private MissionResult missionResult;

    public FlightMission(String name, LocalDateTime startDate, LocalDateTime endDate, Long missionDistance) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.missionDistance = missionDistance;
        this.missionResult = MissionResult.PLANNED;
        this.setId(++counter);
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

    public Long getMissionDistance() {
        return missionDistance;
    }

    public void setMissionDistance(Long missionDistance) {
        this.missionDistance = missionDistance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public void setAssignedSpaceShip(Spaceship assignedSpaceShip) {
        this.assignedSpaceShip = assignedSpaceShip;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        List<CrewMember> crewMemberList = new ArrayList<>();
        for (Map.Entry<Role, Short> map : assignedSpaceShip.getCrew().entrySet()) {
            Role key = map.getKey();
            Short count = map.getValue();
            List<CrewMember> tmp = assignedCrew.stream()
                    .filter(crewMember -> crewMember.getRole() == key)
                    .collect(Collectors.toList());
            Collections.shuffle(tmp);
            for (CrewMember crew : tmp) {
                if (count == 0) continue;
                crew.setReadyForNextMissions(false);
                crewMemberList.add(crew);
                count--;
            }
        }
        this.assignedCrew = crewMemberList;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightMission)) return false;
        FlightMission mission = (FlightMission) o;
        return getStartDate().equals(mission.getStartDate()) &&
                getEndDate().equals(mission.getEndDate()) &&
                getMissionDistance().equals(mission.getMissionDistance()) &&
                getAssignedSpaceShip().equals(mission.getAssignedSpaceShip()) &&
                getMissionResult() == mission.getMissionResult();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), getMissionDistance(), getAssignedSpaceShip(), getMissionResult());
    }

    @Override
    public String toString() {
        return "FlightMission: " + this.getName() + "\n" +
                "  start date: " + startDate + "\n" +
                "  end date: " + endDate + "\n" +
                "  mission distance =" + missionDistance + "\n" +
                "  assigned spaceship:" + assignedSpaceShip + "\n" +
                "  mission result:" + missionResult;
    }
}
