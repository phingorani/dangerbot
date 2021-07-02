package com.waffle.dangerbot.utilService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvDownloadUtilService {

    private static String CSV_FILE_NAME = "userData.csv";

    private static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(CsvDownloadUtilService::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static File dataArrayToCSV(List<String[]> dataLines) throws IOException {
        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(CsvDownloadUtilService::convertToCSV)
                    .forEach(pw::println);
        }
        if (!csvOutputFile.exists()) {
            throw new FileNotFoundException();
        }
        else{
            return csvOutputFile;
        }
    }

}
