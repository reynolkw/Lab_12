import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Logs to console the file name, lines, words, and characters of a chosen text file
 * @author Kuyper Reynolds
 */
public class Main {
    public static void main(String[] args) {
        // prepare to store the output data
        String fileName = "";
        int lines = 0, words = 0, characters = 0;

        // target JFileChooser to program directory
        JFileChooser dialog = new JFileChooser();
        File dialogTarget = new File(System.getProperty("user.dir"));
        dialog.setCurrentDirectory(dialogTarget);

        // open JFileChooser and run document statistics if provided a file
        try {
            if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                // File, Path to scan
                File selectedFile = dialog.getSelectedFile();
                Path selectedFilePath = selectedFile.toPath();

                // record file name
                fileName = selectedFilePath.getFileName().toString();

                // boilerplate file input
                InputStream in = new BufferedInputStream(Files.newInputStream(selectedFilePath, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while (reader.ready()){
                    String record = reader.readLine();
                    // increment line count each time a line is read from the file
                    lines++;

                    // split the line on the space character
                    // add the number of elements (words) to the word count
                    words += record.split("\\s").length;

                    // add the number of characters to the character count
                    characters += record.length();
                }
                // close file and clear buffer
                reader.close();
            }
        // boilerplate error handling from support videos
        } catch (FileNotFoundException e){
            System.out.println("ERROR: File not found.");
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        // output data to console
        System.out.println("TEXT FILE REPORT");
        System.out.printf("%10s: '%s'\n", "File Name", fileName);
        System.out.printf("%10s: %d\n", "Lines", lines);
        System.out.printf("%10s: %d\n", "Words", words);
        System.out.printf("%10s: %d\n", "Characters", characters);
    }
}