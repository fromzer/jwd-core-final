package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {
    public static final CrewServiceImpl INSTANCE = new CrewServiceImpl();
    private static Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);

    private CrewServiceImpl() {
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMemberCriteria findCriteria = (CrewMemberCriteria) criteria;
        return findAllCrewMembers().stream()
                .filter(crewMember -> findCriteria.getId() != null ? findCriteria.getId() == crewMember.getId() : true)
                .filter(crewMember -> findCriteria.getName() != null ? findCriteria.getName().equals(crewMember.getName()) : true)
                .filter(crewMember -> findCriteria.getRank() != null ? findCriteria.getRank() == crewMember.getRank() : true)
                .filter(crewMember -> findCriteria.getRole() != null ? findCriteria.getRank() == crewMember.getRank() : true)
                .filter(crewMember -> findCriteria.getReadyForNextMissions() != null ? findCriteria.getReadyForNextMissions() == crewMember.getReadyForNextMissions() : true)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMemberCriteria findCriteria = (CrewMemberCriteria) criteria;
        return findAllCrewMembers().stream()
                .filter(crewMember -> findCriteria.getId() != null ? findCriteria.getId() == crewMember.getId() : true)
                .filter(crewMember -> findCriteria.getName() != null ? findCriteria.getName().equals(crewMember.getName()) : true)
                .filter(crewMember -> findCriteria.getRank() != null ? findCriteria.getRank() == crewMember.getRank() : true)
                .filter(crewMember -> findCriteria.getRole() != null ? findCriteria.getRank() == crewMember.getRank() : true)
                .filter(crewMember -> findCriteria.getReadyForNextMissions() != null ? findCriteria.getReadyForNextMissions() == crewMember.getReadyForNextMissions() : true)
                .findAny();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        List<CrewMember> crewMemberList = findAllCrewMembers();
        for (CrewMember cr : crewMemberList) {
            if (cr.getId().equals(crewMember.getId())) {
                cr.setRank(crewMember.getRank());
                cr.setRole(crewMember.getRole());
                cr.setName(crewMember.getName());
                cr.setReadyForNextMissions(crewMember.getReadyForNextMissions());
                return cr;
            }
        }
        return null;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember, FlightMission mission) throws RuntimeException {
        mission.getAssignedCrew().add(crewMember);
    }

    @Override
    public CrewMember createCrewMember(Object... args) {
        CrewMember crewMember = CrewMemberFactory.INSTANCE.create(args);

        Collection<CrewMember> crewMemberStorage =
                NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);

        if (crewMemberStorage.contains(crewMember))
            try {
                throw new DuplicateEntityException(crewMember.getClass().toString());
            } catch (DuplicateEntityException e) {
                logger.info("Duplicated entity ", crewMember);
                return null;
            }
        crewMemberStorage.add(crewMember);
        return crewMember;
    }
}
