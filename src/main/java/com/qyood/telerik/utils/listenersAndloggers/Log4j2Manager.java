package com.qyood.telerik.utils.listenersAndloggers;

import com.qyood.telerik.utils.files.PropertyReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.By;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;


public class Log4j2Manager {
    private static final PropertyReader propertyReader = new PropertyReader("./src/main/resources/LoggingManager.properties");
    private static final boolean AUTO_CLEAR_LOG_FILE = Boolean.parseBoolean(propertyReader.getProperty("autoClearLogFile"));

    //Log4j2 is good for multi-threading environment by default
    //ThreadLocal is used to ensure/force thread-safety
    private static final ThreadLocal<Logger> logger = new ThreadLocal<>();
    private static LogHelper logHelper = new LogHelper();

    //PropertiesFile containing the values for the LoggingManager :
    //logFilePath - log4j2PropertiesFilePath - autoClearLogFile - useExceptionLoggingModule instead of throwing them directly.
    private static final String LOGGING_PROPERTIES_FILE = "./src/main/resources/LoggingManager.properties";
    private static final String LOG4J2_PROPERTIES_FILE_PATH = propertyReader.getProperty("log4j2PropertiesFilePath");
    private static final String LOG_FILE_PATH = propertyReader.getProperty("logFilePath");
    private static final boolean USE_EXCEPTION_LOGGING = Boolean.parseBoolean(propertyReader.getProperty("useLoggingManagerForExceptions"));

    //Log Levels
    private static final Level INFO = Level.INFO;
    private static final Level TRACE = Level.TRACE;
    private static final Level ERROR = Level.ERROR;
    private static final Level DEBUG = Level.DEBUG;
    private static final Level FATAL = Level.FATAL;
    private static final Level WARN = Level.WARN;

    //Coloring Log Messages
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    /**
     * @Scope: This Method is used to initialize the Logger, please note you need to place the log4j2 Properties file path,
     * Which is by default is expected to be at "main/resources".
     * Provide the LoggerClassName you want to be printed to the console
     */
    private static void initializeLogger() {
        try {
            Configurator.initialize(null, LOG4J2_PROPERTIES_FILE_PATH);

            //Provide the Logger name to be printed to the console
            //LoggerName to be printed to the console e.g. -> LoggingManager.class.getSimpleName()
            logger.set(LogManager.getLogger(Log4j2Manager.class.getSimpleName()));
            if (AUTO_CLEAR_LOG_FILE) {
                Path logFilePath = Path.of(LOG_FILE_PATH);
                Path logDir = logFilePath.getParent();

                if (logDir != null && !Files.exists(logDir)) {
                    Files.createDirectories(logDir);
                }

                // Create or replace the log file
                Files.writeString(logFilePath, "", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
            Log4j2Manager.logStatements("Log4j2 Properties File can be found at : ", LOG4J2_PROPERTIES_FILE_PATH);
            Log4j2Manager.logStatements("LoggingManager Properties File can be found at : ", LOGGING_PROPERTIES_FILE);
        } catch (Throwable e) {
            if (USE_EXCEPTION_LOGGING) {
                logErrorLogMessage(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static void logAction(String message, By by, String... messageCont){
        logHelper.logAction(message, by, messageCont);
    }

    /**
     * @Scope: This method is used with "logInfoLogMessage" to log Attributes/Values Logs, it's a general method
     * That can be used to print any Number of attributes with their corresponding values
     * @Params: attributeValuePairs -> the required Attributes and Values pairs to be printed.
     * Please note that Attributes and values must be provided in pairs else it will throw an exception
     * e.g. -> (attribute1, value1, attribute2, value2...etc.)
     */
    public static void logAttributes(String... attributeValuePairs) {
        if (attributeValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Attributes and values must be provided in pairs.");
        }

        StringBuilder logMessage = logMessageBuilder();
        for (int i = 0; i < attributeValuePairs.length; i += 2) {
            String attribute = attributeValuePairs[i];
            String value = attributeValuePairs[i + 1];
            logMessage.append(attribute).append(" : ").append(value);
            if (i < attributeValuePairs.length - 2) {
                logMessage.append(" || ");
            }
        }

        logInfoLogMessage(logMessage.toString());
    }

    /**
     * @Scope: This method is used with "logInfoLogMessage" to log Statements Logs, it's a general method
     * That can be used to print 1 MainStatements and any other statements required with highlighting (different coloring)
     * @Params: mainStatement -> the required main statement to be printed (Must be provided).
     * highlightedStatements -> Any extra statements that needs to be highlighted (Doesn't have to be provided)
     * if not provided it will only print the MainStatement to the console
     */

    public static void logStatements(String mainStatement, String... highlightedStatements) {
        StringBuilder logMessage = logMessageBuilder(mainStatement);
        for (String highlightedStatement : highlightedStatements) {
            logMessage.append(highlightedStatement);
        }
        logInfoLogMessage(logMessage.toString());
    }

    /**
     * @Scope: This method is the to be used for ErrorLevel Logs for all the logMessages.
     * Please note that you need to follow the stack trace in case of anything else being printed this is just a demo
     * TO BE USED WITH CAUTION!!!!!!!!!!!!!
     */
    public static void logErrorLogMessage(Throwable... rootCause) {
        String exceptionMsg = rootCause[0].getLocalizedMessage();

        String rootCauseMsg = rootCause[0].getCause() == null
                ? "" :
                "\nRootCauseFailure: " + "\n" + Arrays.toString(Arrays.stream(new Throwable[]{rootCause[0].getCause()}).toArray());
        String message = logMessageBuilder(exceptionMsg, rootCauseMsg).toString();

        //Can be adjusted according to the expected stackTrace
        //MethodName to be printed to the console .e.g. -> Thread.currentThread().getStackTrace()[1].getMethodName()
        logMessageWithLevel(ERROR, Thread.currentThread().getStackTrace()[2].getMethodName() + "\n", RED + message + RESET);

        // TO BE USED WITH CAUTION
        //Enable it if you want the execution to be marked as failed else keep it disabled
//        Assert.fail(message);
    }


    /**
     * @Scope: This method is the to be used for FatalLevel Logs for all the logMessages,
     */
    public static void logFatalLogMessage(String... statements) {
        logMessageWithLevel(FATAL, RED + Arrays.toString(statements) + RESET);
    }

    /**
     * @Scope: This method is the to be used for WarnLevel Logs for all the logMessages,
     */
    public static void logWarnLogMessage(String... statements) {
        logMessageWithLevel(WARN, YELLOW + Arrays.toString(statements) + RESET);
    }

    /**
     * @Scope: This method is the to be used for InfoLevel Logs for all the logMessages,
     */
    public static void logInfoLogMessage(String... statements) {
        logMessageWithLevel(INFO, statements);
    }

    /**
     * @Scope: This method is the to be used for TraceLevel Logs for all the logMessages,
     */

    public static void logTraceLogMessage(String... statements) {
        logMessageWithLevel(TRACE, statements);
    }

    /**
     * @Scope: This method is the to be used for DebugLevel Logs for all the logMessages,
     * P.S : for DEBUG Level you will want to fix the "threshold.level" at log4j2.properties to capture debug level logs
     */
    public static void logDebugLogMessage(String... statements) {
        logMessageWithLevel(DEBUG, statements);
    }

    /**
     * @Scope: This method is the LoggerInitializeWithLevel for all the logMessages,
     * it will be called by all the other logLevel methods
     */
    private static void logMessageWithLevel(Level level, String... statements) {
        if (logger.get() == null) {
            initializeLogger();
        }
        logger.get().log(level, logMessageBuilder(statements));
    }

    /**
     * @Scope: This method is the MessageBuilder for all the logMessages,
     * it will be called by all the other methods
     */
    private static StringBuilder logMessageBuilder(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            stringBuilder.append(s);
        }
        return stringBuilder;
    }

    /**
     * @Scope: This Method is used to Access customLogBuilder from the LoggingManager class
     */
    public static CustomLogBuilder customLogBuilder() {
        if (logger.get() == null) {
            initializeLogger();
        }
        return new CustomLogBuilder();
    }

    /**
     * @Scope: The class used to build Custom LogMessages - Message, Level and Source.
     */
    public static class CustomLogBuilder {
        private final StringBuilder messageBuilder = new StringBuilder();
        private LogLevel logLevel;
        private String source;

        private CustomLogBuilder() {
            // Private constructor to force the use of the builder
        }

        /**
         * @Scope: Message Setter.
         */
        public CustomLogBuilder withMessage(String message) {
            messageBuilder.append(message);
            return this;
        }

        /**
         * @Scope: LogLevel Setter.
         */
        public CustomLogBuilder withLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        /**
         * @Scope: To be used to handle if not passing any LogLevel Enum to the withLogLevel it will default to INFO level.
         */
        public CustomLogBuilder withLogLevel() {
            return withLogLevel(LogLevel.INFO);
        }

        /**
         * @Scope: Source Setter.
         */
        public CustomLogBuilder withSources(String source) {
            this.source = source;
            return this;
        }

        /**
         * @Scope: The Actual Log builder method, this method must be called at the end to confirm the log building with the
         * customized attributes - Message, logLevel & Source.
         * P.S : for DEBUG Level you will want to fix the "threshold.level" at log4j2.properties to capture debug level logs
         */
        public CustomLogBuilder buildLog() {
            CustomLog customLog = new CustomLog(messageBuilder.toString(), logLevel, source);
            switch (logLevel) {
                case DEBUG:
                    logger.get().debug(customLog);
                    break;
                case WARNING:
                    logger.get().warn(customLog);
                    break;
                case ERROR:
                    logger.get().error(customLog);
                    break;
                case FATAL:
                    logger.get().fatal(customLog);
                    break;
                case TRACE:
                    logger.get().trace(customLog);
                    break;
                // Add more cases if needed
                default:
                    logger.get().info(customLog);
                    break;
            }
            return new CustomLogBuilder();
        }
    }

    //ENUM block to be used for presenting the LogLevels supported.
    public enum LogLevel {
        INFO, WARNING, ERROR, DEBUG, TRACE, FATAL
    }

    /**
     * @param logLevel Unused field can be used if you want to have logLevel appended to the Message and add it to the toString method.
     * @param source   Unused field can be used if you want to have source appended to the Message and add it to the toString method.
     * @Scope: This class is the appending the required details to your logger,
     * it's called inside the CustomLoggerBuilder class
     */
        private record CustomLog(String message, LogLevel logLevel, String source) {

        //Unused field can be used if you want to have source appended to the Message and add it to the toString method.
            @Override
            public String toString() {
                return message;

    //            return "CustomLog{" +
    ////                    Demo for appending Msg to the generated statement
    //                    "message='" + message + '\'' +
    ////                    Demo for appending logLevel to the generated statement
    //                    ", logLevel=" + logLevel +
    ////                    Demo for appending Source to the generated statement
    //                    ", source='" + source + '\'' +
    //                    '}';
            }
        }
}
