package com.javalab.contacts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
}
