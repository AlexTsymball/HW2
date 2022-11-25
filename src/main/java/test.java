import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String args[]) {
        String input = "1 fish 2 fish red fish blue fish";
        // \\s* means 0 or more repetitions of any whitespace character
        // fish is the pattern to find
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(input).useDelimiter("\\s*fish\\s*");
//        System.out.println(sc.);
        System.out.println(sc.nextInt());   // prints: 1
        System.out.println(sc.nextInt());   // prints: 2
        System.out.println(sc.next());      // prints: red
        System.out.println(sc.next());      // prints: blue
        //close the scanner
        sc.close();


        try (FileChannel in = new
                FileInputStream("src/main/resources/inputFileForTask1.xml").getChannel();
             FileChannel out = new
                     FileOutputStream("src/main/resources/toFileTask1.xml").getChannel()) {
            int bufferSize = 1024;
            if (bufferSize > in.size()) {
                bufferSize = (int) in.size();
            }
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            System.out.println(bufferSize);
            System.out.println(in.size());
            Charset charset = StandardCharsets.UTF_8;
            Pattern pattern = Pattern.compile("[sur]*name\\s*=\\s*\"([^\\\"]*)\"\\s*[sur]*name\\s*=\\s*\"([^\\\"]*)\"");
//            Pattern pattern = Pattern.compile("[sur]*name\\s*=\\s*\"([^\"]*)\"");
//

            while (in.read(buffer) != -1) {
                buffer.flip(); // Підготуємо до

//                String str = charset.decode(buffer).toString();
//                System.out.println(str);
                Matcher matcher = pattern.matcher(charset.decode(buffer).toString());
                while (matcher.find()) {
                    System.out.println("Matches:" + matcher.find());
                    System.out.println("Matches:" + matcher.group());
//                System.out.println("Matches:" + matcher.matches());
                    System.out.println("Matches:" + matcher.group(1));
                    System.out.println("Matches:" + matcher.group(2));

                }


//                System.out.println(charset.decode(buffer).toString());
//                System.out.println();
                System.out.println("END LINE");
                buffer.position(0);
//                charset.decode(buffer);
                out.write(buffer);

                buffer.clear(); // Підготуємо до читання
            }
        } catch (IOException e) {


        }


    }

}   