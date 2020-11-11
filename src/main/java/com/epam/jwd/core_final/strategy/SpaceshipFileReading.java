package com.epam.jwd.core_final.strategy;

import com.epam.jwd.core_final.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpaceshipFileReading implements FileReadingStrategy {
    public static final SpaceshipFileReading INSTANCE = new SpaceshipFileReading();
    private static Logger logger = LoggerFactory.getLogger(SpaceshipFileReading.class);

    private SpaceshipFileReading() {
    }

    @Override
    public List<String[]> readAndParseInputFile(String fileName) {
        List<String> tmpList = null;
        try {
            tmpList = Files.lines(Paths.get(fileName))
                    .filter(str -> !(str.startsWith("#")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.info("File not found: ", fileName);
        }
        return parse(tmpList);
    }

    private List<String[]> parse(List<String> tmpList) {
        List<String[]> resultList = new ArrayList<>();
        String[] split = new String[0];
        for (String str : tmpList) {
            resultList.add(str.split("[;]"));
        }
        return resultList;
    }

    public Map<Role, Short> parseAndCreateMap(String strObj) {
        List<String[]> listStr = new ArrayList<>();
        String[] split = strObj.split("[{,}]");
        for (String str : split) {
            if (str.contains(":")) {
                listStr.add(str.split(":"));
            }
        }
        return listToMap(listStr);
    }

    private Map<Role, Short> listToMap(List<String[]> list) {
        Map<Role, Short> tmpMap = new HashMap<>();
        for (String[] str : list) {
            tmpMap.put(Role.resolveRoleById(Integer.parseInt(str[0])), Short.parseShort(str[1]));
        }
        return tmpMap;
    }
}
