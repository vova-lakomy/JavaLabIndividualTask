package com.javalab.contacts.util;

import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class CustomFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptLoader.class);
    private static ContactDtoRepository repository = new ContactDtoRepositoryImpl();
    private static final int RANDOM_STRING_CHARS_COUNT = 6;


    private CustomFileUtils() {
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
        logger.debug("generating random string with {} chars",charsCount);
        StringBuilder stringBuilder = new StringBuilder("-");
        Random random = new Random();
        for (int i = 0; i < charsCount; i++) {
            char ch = (char) ((Math.abs(random.nextInt()) % 25) + 97);      //(0+97) - 'a'  (25+97) - 'z'
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public static String definePersonalDirectory(HttpServletRequest request) {
        String contactIdString = request.getParameter("contactId");
        logger.debug("defining personal directory for contact with id");
        String personalLink = null;
        if (isNotBlank(contactIdString)){
            Integer contactId = Integer.parseInt(contactIdString);
            personalLink = repository.getPersonalLink(contactId);
        }
        if (personalLink == null){
            personalLink = (String) request.getAttribute("personalLink");
        }
        if (personalLink == null){
            personalLink = request.getParameter("lastName")
                    + generateRandomString(RANDOM_STRING_CHARS_COUNT);
            request.setAttribute("personalLink",personalLink);
        }
        return personalLink;
    }

    public static String defineFileName(Part part, File saveDir) {
        if (part != null) {
            String submittedFileName
                    = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            return submittedFileName;
        } else {
            return "no-name";
        }
    }
}
