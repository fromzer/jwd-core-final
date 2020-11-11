package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.util.FileRefresherUtil;
import com.epam.jwd.core_final.util.MenuUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
        MenuUtil.IndexPage();
        System.out.println("1. Show spaceship\n" +
                "2. Show missions\n" +
                "3. Show crew members\n" +
                "4. Create mission\n" +
                "5. Update mission details\n" +
                "6. Mission info\n" +
                "7. Save mission in JSON\n" +
                "8. Save missions in JSON\n" +
                "q. Quit game\n");
    }

    default void handleUserInput() {
        Date dateStart = new Date();
        Scanner scanner = new Scanner(System.in);
        String str = null;
        while (str != "q") {
            str = scanner.nextLine();
            switch (str) {
                case "1":
                    MenuUtil.showSpaceships();
                    break;
                case "2":
                    MenuUtil.showMissions();
                    break;
                case "3":
                    MenuUtil.showCrewMembers();
                    break;
                case "4":
                    MenuUtil.createMissions();
                    break;
                case "5":
                    MenuUtil.updateMissionDetails();
                    break;
                case "6":
                    MenuUtil.missionInfo();
                    break;
                case "7":
                    MenuUtil.writeMissionToJSON();
                    break;
                case "8":
                    MenuUtil.writeMissionStorageToJSON();
                    break;
                case "q":
                    return;
                default:
                    printAvailableOptions();
            }
            Date endDate = new Date();
            try {
                FileRefresherUtil.INSTANCE.checkTime(dateStart, endDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printAvailableOptions();
        }
    }
}
