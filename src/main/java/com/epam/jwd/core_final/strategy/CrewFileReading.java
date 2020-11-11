package com.epam.jwd.core_final.strategy;

import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CrewFileReading implements FileReadingStrategy {
    public static final CrewFileReading INSTANCE = new CrewFileReading();
    private static Logger logger = LoggerFactory.getLogger(CrewFileReading.class);

    private CrewFileReading() {
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
        return parseString(tmpList);
    }

    private List<String[]> parseString(List<String> tmpList) {
        List<String> resultList = new ArrayList<>();
        String[] split = new String[0];
        for (String str : tmpList) {
            split = str.split(";");
        }
        Collections.addAll(resultList, split);
        return resultList.stream()
                .map(str -> str.split(","))
                .collect(Collectors.toList());
    }
}
