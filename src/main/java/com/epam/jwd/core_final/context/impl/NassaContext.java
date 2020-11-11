package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.strategy.CrewFileReading;
import com.epam.jwd.core_final.strategy.SpaceshipFileReading;
import com.epam.jwd.core_final.util.EntityReaderUtil;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// todo
public class NassaContext implements ApplicationContext {
    private static NassaContext instance;

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        if (instance == null) {
            instance = new NassaContext();
        }
        return instance;
    }

    static Logger logger = LoggerFactory.getLogger(NassaContext.class);
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> flightMissions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if (tClass.getSimpleName().equals(CrewMember.class.getSimpleName())) {
            return (Collection<T>) crewMembers;
        } else if (tClass.getSimpleName().equals(Spaceship.class.getSimpleName())) {
            return (Collection<T>) spaceships;
        } else if (tClass.getSimpleName().equals(FlightMission.class.getSimpleName())) {
            return (Collection<T>) flightMissions;
        }
        return null;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() {
        //read properties
        PropertyReaderUtil.loadProperties();
        ApplicationProperties appProp = PropertyReaderUtil.readProperties();
        //read files
        EntityReaderUtil.INSTANCE.setFilename((appProp.getInputRootDir() + appProp.getCrewFileName()));

        List<String[]> crewList = EntityReaderUtil.INSTANCE.readAndParseInputFile(CrewFileReading.INSTANCE);
        EntityReaderUtil.INSTANCE.setFilename((appProp.getInputRootDir() + appProp.getSpaceshipsFileName()));
        List<String[]> spaceshipList = EntityReaderUtil.INSTANCE.readAndParseInputFile(SpaceshipFileReading.INSTANCE);
        //
        for (String[] crewStr : crewList) {
            CrewServiceImpl.INSTANCE.createCrewMember(crewStr);
        }

        for (String[] spaceshipStr : spaceshipList) {
            SpaceshipServiceImpl.INSTANCE.createSpaceship(spaceshipStr);
        }
    }
}
