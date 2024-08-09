package com.teamremastered.endrem.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileUtils {

    public static ArrayList<String> getFilesFromDirectory(String path, String extension) {
        ArrayList<String> result = new ArrayList<String>();
        File[] files = new File(path).listFiles((dir, name) -> name.endsWith(extension));

        for (File file : files) {
            if (file.isFile()) {
                result.add(file.getName());
            }
        }

        return result;
    }

    public static ArrayList<String> createStringArrayList(String... strings) {
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, strings);
        return result;
    }
}
