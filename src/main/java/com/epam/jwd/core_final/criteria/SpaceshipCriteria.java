package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions;

    private SpaceshipCriteria() {
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static class Builder extends Criteria<Spaceship> {
        private SpaceshipCriteria spaceshipCriteria;

        public Builder() {
            spaceshipCriteria = new SpaceshipCriteria();
        }

        public Builder setCrew(Map<Role, Short> crew) {
            spaceshipCriteria.crew = crew;
            return this;
        }

        public Builder setFlightDistance(Long flightDistance) {
            spaceshipCriteria.flightDistance = flightDistance;
            return this;
        }

        public Builder setIsReadyForNextMissions(Boolean isReadyForNextMissions) {
            spaceshipCriteria.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public Builder setName(String name) {
            spaceshipCriteria.setName(name);
            return this;
        }

        public Builder setId(Long id) {
            spaceshipCriteria.setId(id);
            return this;
        }

        public SpaceshipCriteria build() {
            return spaceshipCriteria;
        }
    }
}
