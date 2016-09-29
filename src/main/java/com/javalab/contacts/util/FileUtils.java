package com.javalab.contacts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Random;

public final class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptLoader.class);


    private FileUtils() {
    }

    /**
     * deletes file by specified path
     * @param path file pat to delete
     * @return
     */
    public static boolean deleteFile(String path) {
        logger.debug("trying to delete file with path " + path);
        File file = new File(path);
        return file.exists() && file.delete();
    }

    /**
     * renames files
     * @param oldFile file to rename
     * @param newFile new file name
     * @return
     */
    public static boolean renameFile(File oldFile, File newFile){
        logger.debug("trying to rename files {} >> {}", oldFile, newFile);
        return oldFile.renameTo(newFile);
    }

    /**
     * creates a string with random chars starting with '-'
     * @param charsCount length of the string to create
     * @return created string
     */
    public static String generateRandomString(int charsCount){
        StringBuilder stringBuilder = new StringBuilder("-");
        Random random = new Random();
        for (int i = 0; i < charsCount; i++) {
            char ch = (char) ((Math.abs(random.nextInt()) % 25) + 65);
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
}
