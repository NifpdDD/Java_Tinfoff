package edu.pr3;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import picocli.CommandLine;

@CommandLine.Command(name = "LogAnalyzer", description = "Parse and analyze NGINX log files")
public class ArgAnalyzer {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
    public static final String TIME = "T00:00:00Z";

    private ArgAnalyzer() {

    }

    @Getter @CommandLine.Option(names = {"--path"}, description = "Path or URL to NGINX log files", required = true)
    private static String logPath;

    @Getter @CommandLine.Option(names = {"--from"},
                                description = "Start date (format: yyyy-MM-dd)",
                                required = false,
                                defaultValue = "-")
    private static String fromDate;

    @Getter @CommandLine.Option(names = {"--to"},
                                description = "End date (format: yyyy-MM-dd)",
                                required = false,
                                defaultValue = "-")
    private static String toDate;

    @Getter @CommandLine.Option(names = {"--format"},
                                description = "Output format (markdown or adoc)",
                                defaultValue = "markdown")
    private static String outputFormat;

    public static LocalDateTime getFromDateAsOffsetDateTime() {
        if (fromDate.equals("-")) {
            return LocalDateTime.MIN.atOffset(ZoneOffset.MIN).toLocalDateTime();
        }
        return OffsetDateTime.parse(fromDate + TIME, DATE_TIME_FORMATTER).toLocalDateTime();

    }

    public static LocalDateTime getToDateAsOffsetDateTime() {
        if (toDate.equals("-")) {
            return LocalDateTime.MAX.atOffset(ZoneOffset.MAX).toLocalDateTime();
        }
        return OffsetDateTime.parse(toDate + TIME, DATE_TIME_FORMATTER).toLocalDateTime();
    }

    public static void parseArgs(String[] args) {
        CommandLine commandLine = new CommandLine(new ArgAnalyzer());
        commandLine.parseArgs(args);
    }

}
