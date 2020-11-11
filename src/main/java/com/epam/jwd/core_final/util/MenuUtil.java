package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MenuUtil {
    private static Logger logger = LoggerFactory.getLogger(MenuUtil.class);

    public static void IndexPage() {
        System.out.println(" ╔═════════════════════════════════════════════════════════════════════════╗");
        System.out.println(" ║                  Welcome to SpaceCraft!                                 ║ ");
        System.out.println(" ║  A team of space rangers is waiting for interesting missions!           ║ ");
        System.out.println(" ╚═════════════════════════════════════════════════════════════════════════╝ ");
    }

    public static void showSpaceships() {
        List<Spaceship> spaceshipStorage = SpaceshipServiceImpl.INSTANCE.findAllSpaceships();
        for (Spaceship spaceship : spaceshipStorage) {
            System.out.println(spaceship);
        }
    }

    public static void showMissions() {
        List<FlightMission> flightMissionsStorage = MissionServiceImpl.INSTANCE.findAllMissions();
        for (FlightMission flightMission : flightMissionsStorage) {
            System.out.println(flightMission);
        }
    }

    public static void showCrewMembers() {
        List<CrewMember> crewMembersStorage = CrewServiceImpl.INSTANCE.findAllCrewMembers();
        for (CrewMember crewMember : crewMembersStorage) {
            System.out.println(crewMember);
        }
    }

    public static void showCrewMember() {
        System.out.println("Please insert crew member name:");
        String name = readLine();
        Criteria findCrewCriteria = new CrewMemberCriteria.Builder().setName(name).build();
        CrewMember crewMember = (CrewMember) CrewServiceImpl.INSTANCE.findCrewMemberByCriteria(findCrewCriteria).get();
        System.out.println(crewMember);
    }

    private static String[] createMissionMenu() {
        String[] args = new String[4];
        Scanner scanner = new Scanner(System.in);
        String dateFormat = PropertyReaderUtil.readProperties().getDateTimeFormat();
        System.out.println("Please insert mission name: ");
        args[0] = scanner.nextLine();
        System.out.println("Please insert date start in the format - " + dateFormat + " :");
        args[1] = scanner.nextLine();
        System.out.println("Please insert end date in the format - " + dateFormat + " :");
        args[2] = scanner.nextLine();
        System.out.println("Please insert distance: ");
        args[3] = scanner.nextLine();
        return args;
    }

    public static void createMissions() {
        String[] args = createMissionMenu();
        try {
            assignSpaceshipOnMission(MissionServiceImpl.INSTANCE.createMission(args));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static void assignSpaceshipOnMission(FlightMission flightMission) throws Throwable {
        Scanner scanner = new Scanner(System.in);
        Criteria criteria = new SpaceshipCriteria.Builder()
                .setIsReadyForNextMissions(true)
                .setFlightDistance(flightMission.getMissionDistance())
                .build();
        System.out.println("List of ships is ready for mission: ");
        List<Spaceship> availableSpaceships = SpaceshipServiceImpl.INSTANCE.findAllSpaceshipsByCriteria(criteria);
        for (Spaceship sp : availableSpaceships) {
            System.out.println(sp);
        }
        System.out.println("Choose a ship for mission.\n Please insert ship id: ");
        Long id = Long.parseLong(scanner.nextLine());
        Criteria findShipCriteria = new SpaceshipCriteria.Builder().setId(id).build();
        Spaceship spaceship = (Spaceship) (SpaceshipServiceImpl.INSTANCE.findSpaceshipByCriteria(findShipCriteria))
                .orElseThrow(() -> new UnknownEntityException("Not found"));
        SpaceshipServiceImpl.INSTANCE.assignSpaceshipOnMission(spaceship, flightMission);
        spaceship.setReadyForNextMissions(false);
        //assignCrewOnMission
        flightMission.setAssignedCrew(CrewServiceImpl.INSTANCE.findAllCrewMembers());
        System.out.println("Mission create.");
    }

    public static void missionInfo() {
        System.out.println("Please insert mission name:");
        String name = readLine();
        Criteria<FlightMission> criteria = new FlightMissionCriteria.Builder().setName(name).build();
        FlightMission mission = MissionServiceImpl.INSTANCE.findMissionByCriteria(criteria).get();
        if (!(mission.getMissionResult().equals(MissionResult.PLANNED)) && !(mission.getMissionResult().equals(MissionResult.IN_PROGRESS))) {
            System.out.println(mission);
            return;
        }
        mission.setMissionResult(MissionResult.IN_PROGRESS);
        System.out.println("Mission state: " + mission.getMissionResult());
        int random = new Random().nextInt(3) + 1;
        switch (random) {
            case 1:
                mission.setMissionResult(MissionResult.CANCELLED);
                System.out.println("Mission info: " + mission);
                updateReadyForNextMissions(mission);
                break;
            case 2:
                mission.setMissionResult(MissionResult.FAILED);
                System.out.println("Mission info: " + mission);
                break;
            case 3:
                mission.setMissionResult(MissionResult.COMPLETED);
                System.out.println("Mission info: " + mission);
                updateReadyForNextMissions(mission);
                break;
            default:
                try {
                    throw new InvalidStateException("Not corrected key for mission result");
                } catch (InvalidStateException e) {
                    logger.info("Not corrected key for mission result");
                }
        }
    }

    private static String readLine() {
        String str;
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
        return str;
    }

    private static void updateReadyForNextMissions(FlightMission mission) {
        for (CrewMember crew : mission.getAssignedCrew()) {
            crew.setReadyForNextMissions(true);
        }
    }

    public static void updateMissionDetails() {
        System.out.println("Please insert mission name for update:");
        System.out.println("Update mission details:");
        String name = readLine();
        String[] args = createMissionMenu();
        Criteria<FlightMission> criteria = new FlightMissionCriteria.Builder().setName(name).build();
        FlightMission mission = MissionServiceImpl.INSTANCE.findMissionByCriteria(criteria).get();
        FlightMission missionUpd = MissionServiceImpl.INSTANCE.updateMissionDetails(mission, args);
        System.out.println("Complied!");
        System.out.println("Mission info:\n" + missionUpd.toString());
    }

    public static void writeMissionStorageToJSON() {
        System.out.println("Write all missions to JSON");
        List<FlightMission> missionStorage = MissionServiceImpl.INSTANCE.findAllMissions();
        ApplicationProperties appProp = PropertyReaderUtil.readProperties();
        ObjectMapper mapper = new ObjectMapper();
        String path = appProp.getOutputRootDir() + appProp.getMissionsFileName();
        try {
            mapper.writeValue(new File(path), missionStorage);
        } catch (IOException e) {
            logger.info("Write to file error");
        }
        System.out.println("Missions have been saved to: " + path);
    }

    public static void writeMissionToJSON() {
        System.out.println("Write mission to JSON");
        System.out.println("Please insert mission name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Criteria criteria = new FlightMissionCriteria.Builder().setName(name).build();
        FlightMission mission = null;
        try {
            mission = (FlightMission) (MissionServiceImpl.INSTANCE.findMissionByCriteria(criteria)).get();
        } catch (Throwable throwable) {
            logger.info("Mission is null");
        }
        ApplicationProperties appProp = PropertyReaderUtil.readProperties();
        ObjectMapper mapper = new ObjectMapper();
        String path = appProp.getOutputRootDir() + "mission";
        try {
            mapper.writeValue(new File(path), mission);
        } catch (IOException e) {
            logger.info("Write to file error");
        }
        System.out.println("Mission " + mission.getName() + " has been saved to: " + path);
    }
}
