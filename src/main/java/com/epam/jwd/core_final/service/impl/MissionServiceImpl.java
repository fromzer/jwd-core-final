package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {
    public static final MissionServiceImpl INSTANCE = new MissionServiceImpl();
    private static Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>) NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria findCriteria = (FlightMissionCriteria) criteria;
        return findAllMissions().stream()
                .filter(flightMission -> findCriteria.getId() != null ? findCriteria.getId() == flightMission.getId() : true)
                .filter(flightMission -> findCriteria.getName() != null ? findCriteria.getName().equals(flightMission.getName()) : true)
                .filter(flightMission -> findCriteria.getStartDate() != null ? findCriteria.getStartDate().equals(flightMission.getStartDate()) : true)
                .filter(flightMission -> findCriteria.getEndDate() != null ? findCriteria.getEndDate().equals(flightMission.getEndDate()) : true)
                .filter(flightMission -> findCriteria.getMissionResult() != null ? findCriteria.getMissionResult() == flightMission.getMissionResult() : true)
                .filter(flightMission -> findCriteria.getAssignedCrew() != null ? findCriteria.getAssignedCrew() == flightMission.getAssignedCrew() : true)
                .filter(flightMission -> findCriteria.getAssignedSpaceShip() != null ? findCriteria.getAssignedSpaceShip() == flightMission.getAssignedSpaceShip() : true)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria findCriteria = (FlightMissionCriteria) criteria;
        return findAllMissions().stream()
                .filter(flightMission -> findCriteria.getId() != null ? findCriteria.getId() == flightMission.getId() : true)
                .filter(flightMission -> findCriteria.getName() != null ? findCriteria.getName().equals(flightMission.getName()) : true)
                .filter(flightMission -> findCriteria.getStartDate() != null ? findCriteria.getStartDate().equals(flightMission.getStartDate()) : true)
                .filter(flightMission -> findCriteria.getEndDate() != null ? findCriteria.getEndDate().equals(flightMission.getEndDate()) : true)
                .filter(flightMission -> findCriteria.getMissionResult() != null ? findCriteria.getMissionResult() == flightMission.getMissionResult() : true)
                .filter(flightMission -> findCriteria.getAssignedCrew() != null ? findCriteria.getAssignedCrew() == flightMission.getAssignedCrew() : true)
                .filter(flightMission -> findCriteria.getAssignedSpaceShip() != null ? findCriteria.getAssignedSpaceShip() == flightMission.getAssignedSpaceShip() : true)
                .findFirst();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission, String[] args) {
        FlightMission flightMissionUpd = FlightMissionFactory.INSTANCE.create(args);
        flightMission.setName(flightMissionUpd.getName());
        flightMission.setStartDate(flightMissionUpd.getStartDate());
        flightMission.setEndDate(flightMissionUpd.getEndDate());
        flightMission.setMissionDistance(flightMissionUpd.getMissionDistance());
        flightMission.setMissionResult(flightMissionUpd.getMissionResult());
        return flightMission;
    }

    @Override
    public FlightMission createMission(Object... args) {

        FlightMission flightMission = FlightMissionFactory.INSTANCE.create(args);
        Collection<FlightMission> missionStorage =
                NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class);

        if (missionStorage.contains(flightMission))
            try {
                throw new DuplicateEntityException(flightMission.getClass().toString());
            } catch (DuplicateEntityException e) {
                logger.info("Duplicated entity ", flightMission);
                return null;
            }
        missionStorage.add(flightMission);
        return flightMission;
    }
}
