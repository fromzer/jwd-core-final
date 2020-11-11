package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private Rank rank;
    private Boolean isReadyForNextMissions;

    private CrewMemberCriteria() {
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static class Builder {
        private CrewMemberCriteria crewMemberCriteria;

        public Builder() {
            crewMemberCriteria = new CrewMemberCriteria();
        }

        public Builder setRole(Role role) {
            crewMemberCriteria.role = role;
            return this;
        }

        public Builder setRank(Rank rank) {
            crewMemberCriteria.rank = rank;
            return this;
        }

        public Builder setIsReadyForNextMissions(Boolean isReadyForNextMissions) {
            crewMemberCriteria.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public Builder setName(String name) {
            crewMemberCriteria.setName(name);
            return this;
        }

        public Builder setId(Long id) {
            crewMemberCriteria.setId(id);
            return this;
        }

        public CrewMemberCriteria build() {
            return crewMemberCriteria;
        }
    }
}
