package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.strategy.FileReadingStrategy;

import java.util.List;

public class EntityReaderUtil {
    public static final EntityReaderUtil INSTANCE = new EntityReaderUtil();
    private String filename;

    private EntityReaderUtil() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<String[]> readAndParseInputFile(FileReadingStrategy fileReadingStrategy) {
        return fileReadingStrategy.readAndParseInputFile(filename);
    }
}
