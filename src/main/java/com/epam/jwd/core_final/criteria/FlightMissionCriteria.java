package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long missionDistance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;

    private FlightMissionCriteria() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getMissionDistance() {
        return missionDistance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public static class Builder {
        private FlightMissionCriteria flightMissionCriteria;

        public Builder() {
            flightMissionCriteria = new FlightMissionCriteria();
        }

        public Builder setStartDate(LocalDateTime startDate) {
            flightMissionCriteria.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate) {
            flightMissionCriteria.endDate = endDate;
            return this;
        }

        public Builder setMissionDistance(Long missionDistance) {
            flightMissionCriteria.missionDistance = missionDistance;
            return this;
        }

        public Builder setAssignedSpaceShip(Spaceship assignedSpaceShip) {
            flightMissionCriteria.assignedSpaceShip = assignedSpaceShip;
            return this;
        }

        public Builder setAssignedCrew(List<CrewMember> assignedCrew) {
            flightMissionCriteria.assignedCrew = assignedCrew;
            return this;
        }

        public Builder setMissionResult(MissionResult missionResult) {
            flightMissionCriteria.missionResult = missionResult;
            return this;
        }

        public Builder setName(String name) {
            flightMissionCriteria.setName(name);
            return this;
        }

        public Builder setId(Long id) {
            flightMissionCriteria.setId(id);
            return this;
        }

        public FlightMissionCriteria build() {
            return flightMissionCriteria;
        }
    }
}
