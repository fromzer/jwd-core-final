package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory {
    public static final CrewMemberFactory INSTANCE = new CrewMemberFactory();

    private CrewMemberFactory() {
    }

    @Override
    public CrewMember create(Object... args) {
        return new CrewMember(Role.resolveRoleById(Integer.parseInt((String) args[0])),
                (String) args[1],
                Rank.resolveRankById(Integer.parseInt((String) args[2])));
    }
}
