package com.epam.jwd.core_final.strategy;

import java.util.List;

public interface FileReadingStrategy {
    List<String[]> readAndParseInputFile(String fileName);
}
