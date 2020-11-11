package com.epam.jwd.core_final.domain;

import java.util.Objects;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private static long counter = 0;

    private Role role;
    private Rank rank;
    private Boolean isReadyForNextMissions;

    public CrewMember(Role role, String name, Rank rank) {
        super(name);
        this.role = role;
        this.rank = rank;
        isReadyForNextMissions = true;
        this.setId(++counter);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrewMember)) return false;
        CrewMember that = (CrewMember) o;
        return getRole() == that.getRole() &&
                getRank() == that.getRank() &&
                Objects.equals(isReadyForNextMissions, that.isReadyForNextMissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole(), getRank(), isReadyForNextMissions);
    }

    @Override
    public String toString() {
        return "\n" + this.getId() + ". CrewMember: " + this.getName() + "\n"
                + "  role: " + role + "\n"
                + "  rank: " + rank + "\n"
                + "  ready for next missions: " + isReadyForNextMissions;
    }
}
