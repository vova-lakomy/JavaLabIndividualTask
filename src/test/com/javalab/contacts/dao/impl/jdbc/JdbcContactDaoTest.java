package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.PhoneType;
import com.javalab.contacts.model.enumerations.Sex;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static com.javalab.contacts.dao.impl.jdbc.SqlScriptLoader.loadScript;
import static org.junit.Assert.*;


public class JdbcContactDaoTest {
    @BeforeClass
    public static void setUp() throws Exception {
        loadScript("src/main/resources/db/initDB.sql");
        loadScript("src/main/resources/db/populateDB.sql");
    }

    @Test
    public void get() throws Exception {
        JdbcContactDao contactDao = new JdbcContactDao();

        assertTrue(contactDao.get(2).getPhoneNumber().getPhoneNumber() == 7047143);
    }

    @Test
    public void save() throws Exception {
        JdbcContactDao contactDao = new JdbcContactDao();
        Contact contact = new Contact(null,"firstName1","secondName1","lastName1", LocalDate.now(), Sex.FEMALE,
                "German", MartialStatus.MARRIED,"super.web.site.com","name1@gmail.com","Oracle",new ContactAddress(null,
                "Germany","Giessen", "BlobStrasse",2,45,55250),new HashSet<>(), "./uploads/img/ava.jpg",
                new PhoneNumber(null,71,14,5547851, PhoneType.MOBILE,"lol"));
        contactDao.save(contact);

        assertTrue(contactDao.get(11).equals(contact));

        contact.getAttachments().add(new ContactAttachment(null,"/uploads/test_save1.txt","attachment1",LocalDate.now()));
        contact.getAttachments().add(new ContactAttachment(null,"/uploads/test_save2.txt","attachment2",LocalDate.now()));
        contact.getAttachments().add(new ContactAttachment(null,"/uploads/test_save3.txt","attachment3",LocalDate.now()));
        contact.getAttachments().add(new ContactAttachment(null,"/uploads/test_save4.txt","attachment4",LocalDate.now()));
        contactDao.save(contact);

        assertTrue(contactDao.get(11).equals(contact));
    }

    @Test
    public void delete() throws Exception {
        JdbcContactDao contactDao = new JdbcContactDao();
        contactDao.delete(5);

        assertTrue(null == contactDao.get(5));
    }

}