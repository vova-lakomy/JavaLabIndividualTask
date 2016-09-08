package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.model.ContactAttachment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static com.javalab.contacts.util.SqlScriptLoader.loadScript;
import static org.junit.Assert.*;


public class JdbcContactAttachmentDaoTest {
    @BeforeClass
    public static void setUp() throws Exception {
        loadScript("src/main/resources/initDB.sql");
        loadScript("src/main/resources/populateDB.sql");
    }

    @Test
    public void get() throws Exception {
        JdbcContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
        ContactAttachment attachment = attachmentDao.get(9);
        assertTrue(attachment.getAttachmentLink().equals("./uploads/file9.txt"));
    }

    @Test
    public void save() throws Exception {
        JdbcContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
        ContactAttachment attachment1 = new ContactAttachment(null,"linnnnnnk","this is liink", LocalDate.now());
        ContactAttachment attachment2 = new ContactAttachment(2,"liiiink","this is liink",LocalDate.now());
        attachmentDao.save(attachment1,8);
        attachmentDao.save(attachment2,8);

        attachmentDao.save(null,null);

        assertTrue(null != attachmentDao.get(15));
        assertTrue(attachment2.equals(attachmentDao.get(2)));
    }

    @Test
    public void delete() throws Exception {
        JdbcContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
        attachmentDao.delete(11);

        assertTrue(null == attachmentDao.get(11));
    }

    @Test
    public void getByContactId() throws Exception {
        JdbcContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();

        assertTrue(attachmentDao.getByContactId(2).size() == 3);
    }
}