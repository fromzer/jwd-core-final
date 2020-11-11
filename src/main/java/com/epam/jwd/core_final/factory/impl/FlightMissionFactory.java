package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DateSequenceException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightMissionFactory implements EntityFactory {
    private static Logger logger = LoggerFactory.getLogger(NassaContext.class);
    public static final FlightMissionFactory INSTANCE = new FlightMissionFactory();

    @Override
    public FlightMission create(Object... args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PropertyReaderUtil.readProperties().getDateTimeFormat());
        LocalDateTime dateStart = LocalDateTime.parse((String) args[1], formatter);
        LocalDateTime dateEnd = LocalDateTime.parse((String) args[2], formatter);
        if (dateStart.isAfter(dateEnd)) {
            try {
                throw new DateSequenceException("Date not valid!");
            } catch (DateSequenceException e) {
                logger.info("Date not valid!");
            }
        }
        return new FlightMission((String) args[0],
                LocalDateTime.parse((String) args[1], formatter),
                LocalDateTime.parse((String) args[2], formatter),
                Long.parseLong((String) args[3]));
    }
}
