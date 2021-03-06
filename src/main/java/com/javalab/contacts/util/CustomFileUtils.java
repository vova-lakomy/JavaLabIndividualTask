package com.javalab.contacts.util;

import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class CustomFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(CustomFileUtils.class);
    private static ContactRepository repository = new ContactRepositoryImpl();
    private static final int RANDOM_STRING_CHARS_COUNT = 6;

    private CustomFileUtils() {
    }

    public static boolean deleteFile(String path) {
        logger.debug("trying to delete file with path " + path);
        File file = new File(path);
        return file.exists() && file.delete();
    }

    public static boolean renameFile(File oldFile, File newFile) {
        logger.debug("trying to rename file [{}] to [{}]", oldFile, newFile);
        return oldFile.renameTo(newFile);
    }

    public static String generateRandomString(int charsCount) {
        logger.debug("generating random string with {} chars", charsCount);
        StringBuilder stringBuilder = new StringBuilder("-");
        Random random = new Random();
        for (int i = 0; i < charsCount; i++) {
            char ch = (char) ((Math.abs(random.nextInt()) % 25) + 97);      //(0+97) - 'a'  (25+97) - 'z'
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public static String definePersonalDirectory(HttpServletRequest request) throws ConnectionFailedException {
        String contactIdString = request.getParameter("contactId");
        logger.debug("defining personal directory for contact with id");
        String personalLink = null;
        if (isNotBlank(contactIdString)) {
            Integer contactId = Integer.parseInt(contactIdString);
            personalLink = repository.getPersonalLink(contactId);
        }
        if (personalLink == null) {
            personalLink = (String) request.getAttribute("personalLink");
        }
        if (personalLink == null) {
            personalLink = request.getParameter("lastName")
                    + generateRandomString(RANDOM_STRING_CHARS_COUNT);
            request.setAttribute("personalLink", personalLink);
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

    public static String defineAttachmentFileName(HttpServletRequest request, String attachmentIndex, String saveDir) {
        String fileNameFromRequest = request.getParameter("attachmentFileName-" + attachmentIndex);
        if (isBlank(fileNameFromRequest)) {
            fileNameFromRequest = "no-name";
        }
        File resultFile = new File(saveDir + fileNameFromRequest);
        while (resultFile.exists()) {
            String ext = "";
            String fileNameWithoutExt;
            if (fileNameFromRequest.lastIndexOf(".") > 0) {
                ext = fileNameFromRequest.substring(fileNameFromRequest.lastIndexOf('.'));
                fileNameWithoutExt = fileNameFromRequest.substring(0, fileNameFromRequest.lastIndexOf('.'));
            } else {
                fileNameWithoutExt = fileNameFromRequest;
            }
            fileNameFromRequest = fileNameWithoutExt + "(1)" + ext;
            resultFile = new File(saveDir + fileNameFromRequest);
        }
        return fileNameFromRequest;
    }

    public static void writePartToDisk(Part part, String fullUploadPath) {
        try {
            part.write(fullUploadPath);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public static String getFileRandomDir(){
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        return timeStamp + generateRandomString(5);
    }

}
