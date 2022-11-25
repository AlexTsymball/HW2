package task1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {

    private static final String PATH_TO_DIRECTORIES_WITH_FILES = "src/main/files/";
    /**
     * it will find 8 strings:
     * 1,6 - "name" or "surname"
     * 2,7 - whitespace characters with "=" after "name" or "surname" (to preserve formatting). For output file
     * will take formatting that near the name.
     * 3,8 - name or surname
     * 4 - characters that are between name and surname. It use to preserve formatting after deleting surname.
     */
    private static final Pattern FIND_NAME_SURNAME_WITH_FORMATTING_PATTERN
//            = Pattern.compile("\\s(surname|name)(\\s*=\\s*)\"([^\"]*)\"(\\s[^sn]*\\s|\\s)(surname|name)(\\s*=\\s*)\"([^\"]*)\"(\\s?)");
            = Pattern.compile("\\s(surname|name)(\\s*=\\s*)\"([^\"]*)\"(\\s+(?!surname|name).*\\s+|\\s)(surname|name)(\\s*=\\s*)\"([^\"]*)\"");
    /**
     * check the right order of name and surname
     */
    private static final String CHECK_ORDER = " n";

    public static void main(String[] args) {
        Task1 task1 = new Task1();
        try {
            long start = System.currentTimeMillis();
            task1.copyFileWithModificationNameSurname();
            System.out.println(System.currentTimeMillis() - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFileWithModificationNameSurname() throws IOException {
        try (Scanner scanner = new Scanner(
                Path.of(PATH_TO_DIRECTORIES_WITH_FILES + "input/task1/inputFileForTask1.xml"), StandardCharsets.UTF_8)
                .useDelimiter("/>");
             BufferedWriter bufferedWriter = new
                     BufferedWriter(
                     new FileWriter(PATH_TO_DIRECTORIES_WITH_FILES + "output/outputFileForTask1.xml"))) {
            while (scanner.hasNextLine()) {
                String person = scanner.next();
                Matcher matcher = FIND_NAME_SURNAME_WITH_FORMATTING_PATTERN.matcher(person);
                while (matcher.find()) {
                    if (!matcher.group(1).equals(matcher.group(5))) {
                        if (matcher.group().startsWith(CHECK_ORDER)) {
                            person = person.replace(matcher.group(),
                                    " name" + matcher.group(2) + "\"" + matcher.group(3) + " "
                                            + matcher.group(7) + "\"" + matcher.group(4));

                        } else {
                            person = person.replace(matcher.group(),
                                    matcher.group(4) + "name" + matcher.group(6) + "\"" + matcher.group(7)
                                            + " " + matcher.group(3) + "\"");

                        }
                    }
                }
                bufferedWriter.write(person);
                if (scanner.hasNextLine()) {
                    bufferedWriter.write("/>");
                }
            }
        }
    }
}
