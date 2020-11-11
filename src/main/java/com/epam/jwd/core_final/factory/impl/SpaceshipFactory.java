package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.strategy.SpaceshipFileReading;

public class SpaceshipFactory implements EntityFactory {
    public static final SpaceshipFactory INSTANCE = new SpaceshipFactory();

    private SpaceshipFactory() {
    }

    @Override
    public Spaceship create(Object... args) {
        Spaceship spaceship = new Spaceship((String) args[0], SpaceshipFileReading.INSTANCE.parseAndCreateMap((String) args[2]), Long.parseLong((String) args[1]));
        return spaceship;
    }
}
