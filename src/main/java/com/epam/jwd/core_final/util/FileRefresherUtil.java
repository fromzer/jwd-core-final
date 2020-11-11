package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileRefresherUtil {
    public static FileRefresherUtil INSTANCE = new FileRefresherUtil();

    private static Long saveTimeCreateCrew;
    private static Long saveTimeCreateSpaceships;
    private static ApplicationProperties properties = PropertyReaderUtil.readProperties();

    private FileRefresherUtil() {
    }

    public void checkTime(Date dateStart, Date endDate) throws IOException {

        String crewFile = properties.getInputRootDir() + properties.getCrewFileName();
        String spaceshipFile = properties.getInputRootDir() + properties.getSpaceshipsFileName();
        Path crewPath = Paths.get(crewFile);
        Path spaceshipPath = Paths.get(spaceshipFile);
        Long timeCreateFileCrew = Files.getLastModifiedTime(crewPath).toMillis();
        Long timeCreateFileSpaceship = Files.getLastModifiedTime(spaceshipPath).toMillis();
        saveTimeCreateCrew = timeCreateFileCrew;
        saveTimeCreateSpaceships = timeCreateFileSpaceship;
        if (!checkTimeCreate(saveTimeCreateCrew, crewPath, dateStart, endDate)) {
            startInit();
        }
        if (!checkTimeCreate(saveTimeCreateSpaceships, spaceshipPath, dateStart, endDate)) {
            startInit();
        }
    }

    private boolean checkTimeCreate(Long saveTimeCreatedFile, Path filePath, Date dateStart, Date endDate) throws IOException {
        long timeStart = dateStart.getTime();
        long timeEnd = endDate.getTime();
        Long timeFileCreate = Files.getLastModifiedTime(filePath).toMillis();
        if (timeStart - timeEnd > properties.getFileRefreshRate()) {
            if (timeFileCreate != saveTimeCreatedFile) {
                return false;
            }
            return true;
        }
        return true;
    }

    private void startInit() throws IOException {
        String crewFile = properties.getInputRootDir() + properties.getCrewFileName();
        String spaceshipFile = properties.getInputRootDir() + properties.getSpaceshipsFileName();
        Path crewPath = Paths.get(crewFile);
        Path spaceshipPath = Paths.get(spaceshipFile);
        saveTimeCreateCrew = Files.getLastModifiedTime(crewPath).toMillis();
        saveTimeCreateSpaceships = Files.getLastModifiedTime(spaceshipPath).toMillis();
        NassaContext.getInstance().init();
    }
}
