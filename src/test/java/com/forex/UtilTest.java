package com.forex;

import java.io.File;
import java.util.Objects;

public class UtilTest {

    public static String getPathFromFile(String fileName) {
        ClassLoader classLoader = UtilTest.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        return file.getPath();
    }
}
