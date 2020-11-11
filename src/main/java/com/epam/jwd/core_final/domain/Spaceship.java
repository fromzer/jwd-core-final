package com.epam.jwd.core_final.domain;

import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    //todo
    private static long counter = 0;
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions;

    public Spaceship(String name, Map<Role, Short> crew, Long flightDistance) {
        super(name);
        this.crew = crew;
        this.flightDistance = flightDistance;
        isReadyForNextMissions = true;
        this.setId(++counter);
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public void setCrew(Map<Role, Short> crew) {
        this.crew = crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }


    @Override
    public String toString() {
        return this.getId() + ". Spaceship: " + this.getName() + "\n"
                + "  crew: " + crew + "\n"
                + "  flightDistance = " + flightDistance + "\n"
                + "  ready for next missions: " + isReadyForNextMissions + ";\n";
    }
}
