package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {
    public static final SpaceshipServiceImpl INSTANCE = new SpaceshipServiceImpl();
    private static Logger logger = LoggerFactory.getLogger(SpaceshipServiceImpl.class);

    private SpaceshipServiceImpl() {
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria findCriteria = (SpaceshipCriteria) criteria;
        return findAllSpaceships().stream()
                .filter(spaceship -> findCriteria.getId() != null ? findCriteria.getId() == spaceship.getId() : true)
                .filter(spaceship -> findCriteria.getName() != null ? findCriteria.getName() == spaceship.getName() : true)
                .filter(spaceship -> findCriteria.getCrew() != null ? findCriteria.getCrew().equals(spaceship.getCrew()) : true)
                .filter(spaceship -> findCriteria.getFlightDistance() != null ? findCriteria.getFlightDistance() <= spaceship.getFlightDistance() : true)
                .filter(spaceship -> findCriteria.getReadyForNextMissions() != null ? findCriteria.getReadyForNextMissions() == spaceship.getReadyForNextMissions() : true)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria findCriteria = (SpaceshipCriteria) criteria;
        return findAllSpaceships().stream()
                .filter(spaceship -> findCriteria.getId() != null ? findCriteria.getId() == spaceship.getId() : true)
                .filter(spaceship -> findCriteria.getName() != null ? findCriteria.getName() == spaceship.getName() : true)
                .filter(spaceship -> findCriteria.getCrew() != null ? findCriteria.getCrew().equals(spaceship.getCrew()) : true)
                .filter(spaceship -> findCriteria.getFlightDistance() != null ? findCriteria.getFlightDistance() == spaceship.getFlightDistance() : true)
                .filter(spaceship -> findCriteria.getReadyForNextMissions() != null ? findCriteria.getReadyForNextMissions() == spaceship.getReadyForNextMissions() : true)
                .findAny();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        List<Spaceship> spaceshipList = findAllSpaceships();
        for (Spaceship sp : spaceshipList) {
            if (sp.getId().equals(spaceship.getId())) {
                sp.setCrew(spaceship.getCrew());
                sp.setFlightDistance(spaceship.getFlightDistance());
                sp.setReadyForNextMissions(spaceship.getReadyForNextMissions());
                return sp;
            }
        }
        return null;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship, FlightMission mission) throws RuntimeException {
        mission.setAssignedSpaceShip(spaceship);
    }

    @Override
    public Spaceship createSpaceship(Object... args) throws RuntimeException {
        Spaceship spaceship = SpaceshipFactory.INSTANCE.create(args);

        Collection<Spaceship> spaceshipStorage =
                NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class);

        if (spaceshipStorage.contains(spaceship))
            try {
                throw new DuplicateEntityException(spaceship.getClass().toString());
            } catch (DuplicateEntityException e) {
                logger.info("Duplicated entity ", spaceship);
                return null;
            }
        spaceshipStorage.add(spaceship);
        return spaceship;
    }
}
