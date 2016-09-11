package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.javalab.contacts.util.SqlScriptLoader.loadScript;
import static org.junit.Assert.*;


public class JdbcPhoneNumberDaoTest {
    @BeforeClass
    public static void setUp() throws Exception {
        loadScript("src/main/resources/initDB.sql");
        loadScript("src/main/resources/populateDB.sql");
    }

    @Test
    public void get() throws Exception {
        JdbcPhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
        PhoneNumber phoneNumber = phoneNumberDao.get(1);

        assertTrue(phoneNumber.getPhoneNumber() == 7641640);
    }

    @Test
    public void save() throws Exception {
        PhoneNumber phoneNumber1 = new PhoneNumber(null,375,25,1111155, PhoneType.MOBILE,"testCreate");
        PhoneNumber phoneNumber2 = new PhoneNumber(10,222,33,4444444, PhoneType.HOME,"testUpdate");
        JdbcPhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
        phoneNumberDao.save(phoneNumber1,1);
        phoneNumberDao.save(phoneNumber2,2);

        assertTrue(phoneNumber1.equals(phoneNumberDao.get(15)));
        assertTrue(phoneNumber2.equals(phoneNumberDao.get(10)));
    }

    @Test
    public void delete() throws Exception {
        JdbcPhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
        phoneNumberDao.delete(5);

        assertTrue(null == phoneNumberDao.get(5));
    }
}