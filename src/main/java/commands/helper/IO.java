package commands.helper;

import java.io.*;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class to contain many helper functions related to IO and files as needed by
 * specific commands
 */
public class IO {

    /**
     * Reads the entire file given by the String path, returns an array
     * where each element is a line from the file. (Index 0 = first line,
     * index 1 = second line, etc.)
     *
     * @param path Location of file to read from
     * @return A String array where each element is a line from the file.
     */
    public static String[] readAllFileLinesIntoArray(String path) {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(path));
            return Arrays.stream(reader.lines().toArray()).toArray(String[]::new);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.out.println(ex.getLocalizedMessage());
            return null;
        }

    }




    /**
     * Writes a json object to the file specified by the given path.
     * @param jsonObj Object to write to file
     * @param path file to write to
     * @return true if successful, false if not.
     */
    public static boolean writeJson(JSONArray jsonArray, String path) {
        boolean successful = true;
        FileWriter writer = null;

        try {
            writer = new FileWriter(path, false);
            writer.write(jsonArray.toJSONString());
            writer.close();
        }
        catch(IOException e) {
            System.out.println(e.getLocalizedMessage());
            successful = false;
        }

        return successful;
    }


    public static Object readJson(String path) {
        JSONParser parser = new JSONParser();
        Object res;

        try {
            FileReader reader = new FileReader(path);
            res = parser.parse(reader);
        }
        catch(ParseException e) {
            System.out.println(e.getLocalizedMessage());
            res = null;
        }
        catch(IOException e) {
            System.out.println(e.getLocalizedMessage());
            res = null;
        }

        return res;
    }

    /**
     * Checks if a given file at the path exists or not.
     * @param path file to check
     * @return true if file exists, no if not.
     */
    public static boolean fileExists (String path) {
        File file = new File(path);
        return file.exists();
    }
}
