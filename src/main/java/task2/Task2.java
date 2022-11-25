package task2;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import task2.model.TypeAndSum;
import task2.model.Statistics;

import java.io.File;
import java.io.IOException;

public class Task2 {
    private static final String PATH_TO_DIRECTORIES_WITH_FILES = "src/main/files/";

    public static void main(String[] args) throws IOException {
        Task2 task2 = new Task2();
        task2.getStatisticsFromJson2Xml();
    }

    private void getStatisticsFromJson2Xml() throws IOException {
        File[] listInputFiles = filesWithJson(new File(PATH_TO_DIRECTORIES_WITH_FILES + "input/task2"), "json");
        Statistics statistics = new Statistics();
        if (listInputFiles != null) {
            for (File inputFile : listInputFiles) {
                getFineTypeAmountFromJson(inputFile, statistics);
            }

        }

        statistics.groupAndSort();
        writeStatistics2XML(statistics);


    }

    private void getFineTypeAmountFromJson(File inputFile, Statistics statistics) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        try (JsonParser jsonParser = jsonFactory.createParser(inputFile)) {
            TypeAndSum typeAndSum;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                typeAndSum = new TypeAndSum();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String fieldname = jsonParser.getCurrentName();

                    if ("type".equals(fieldname)) {
                        jsonParser.nextToken();
                        typeAndSum.setType(jsonParser.getText());
                    } else if ("fine_amount".equals(fieldname)) {
                        jsonParser.nextToken();
                        typeAndSum.setSumFineAmount(jsonParser.getDoubleValue());
                    }
                }
                statistics.addFines(typeAndSum);

            }

        }


    }

    public File[] filesWithJson(File dir, String json) {
        String end = "." + json;
        return dir.listFiles(
                file -> !file.isDirectory() &&
                        file.getName().endsWith(end)
        );
    }


    private void writeStatistics2XML(Statistics statistics) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.writeValue(new File(PATH_TO_DIRECTORIES_WITH_FILES + "output/statisticsTask2.xml"),
                statistics);
    }
}
