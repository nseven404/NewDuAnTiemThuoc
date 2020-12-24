/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class xuLyTextFile {

    // Lấy path text file như thế nào?
    public static ArrayList<String> docFile(String path) {
        ArrayList<String> listText = new ArrayList();

        try {

            // Tạo path
            String pathFile = layPathFile(path);
            // create a reader
            BufferedReader br = new BufferedReader(new FileReader(pathFile));

            // read until end of file
            String line;
            while ((line = br.readLine()) != null) {
                listText.add(line);
            }

            // close the reader
            br.close();

        } catch (IOException ex) {
            System.out.println("Loi doc file : " + ex.getMessage());
        }

        return listText;
    }

    public static boolean writeTextFile(String path, ArrayList<String> text) throws IOException {

        boolean status = true;

        // Tạo path
        String pathFile = layPathFile(path);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(pathFile))) {
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i));
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Lỗi đọc file : " + ex.getMessage());
        }

        return status;
    }

    public static String layPathFile(String path) {
        String localDir = System.getProperty("user.dir");
        localDir += path;
        return localDir;
    }
}
