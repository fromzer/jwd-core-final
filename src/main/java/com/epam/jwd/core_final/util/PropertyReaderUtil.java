package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();
    private static Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        final String propertiesFileName = "./src/main/resources/application.properties";
        try (InputStream inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            logger.info("File not found!");
        }
    }

    public static ApplicationProperties readProperties() {
        String inputRootDir = properties.getProperty("inputRootDir");
        String outputRootDir = properties.getProperty("outputRootDir");
        String crewFileName = properties.getProperty("crewFileName");
        String missionsFileName = properties.getProperty("missionsFileName");
        String spaceshipsFileName = properties.getProperty("spaceshipsFileName");
        Integer fileRefreshRate = Integer.parseInt(properties.getProperty("fileRefreshRate"));
        String dateTimeFormat = properties.getProperty("dateTimeFormat");
        ApplicationProperties appProperties = new ApplicationProperties(inputRootDir,
                outputRootDir,
                crewFileName,
                missionsFileName,
                spaceshipsFileName,
                fileRefreshRate,
                dateTimeFormat);
        return appProperties;
    }
}
