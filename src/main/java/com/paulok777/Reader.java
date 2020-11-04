package com.paulok777;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {
    public static List<String> getLines(String path) {
        try(Scanner scanner = new Scanner(new FileInputStream(path))) {
            List<String> lines = new ArrayList<>();
            while(scanner.hasNextLine()) {
                lines.add(scanner.nextLine() + "\n");
            }
            return lines;
        } catch (IOException e) {
            e.getCause();
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
