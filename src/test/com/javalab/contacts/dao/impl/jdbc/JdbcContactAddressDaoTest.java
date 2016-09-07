package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.model.ContactAddress;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.javalab.contacts.util.SqlScriptLoader.loadScript;
import static org.junit.Assert.*;


public class JdbcContactAddressDaoTest {
    @BeforeClass
    public static void setUp() throws Exception {
        loadScript("src/main/resources/db/initDB.sql");
        loadScript("src/main/resources/db/populateDB.sql");
    }

    @Test
    public void get() throws Exception {
        JdbcContactAddressDao addressDao = new JdbcContactAddressDao();
        ContactAddress contactAddress = addressDao.get(2);
        assertTrue(contactAddress.getCountry().equals("Belarus"));
    }

    @Test
    public void save() throws Exception {
        ContactAddress address1 = new ContactAddress(null, "Germany", "Berlin", "Grunenstrasse", 112, 1, 54995);
        ContactAddress address2 = new ContactAddress(5, "Germany", "Frankfurt-am-Main", "Kernstrasse", 1, 554, 54915);
        JdbcContactAddressDao addressDao = new JdbcContactAddressDao();
        addressDao.save(address1,1);
        addressDao.save(address2,2);
        assertTrue(address2.equals(addressDao.get(5)));
    }

    @Test
    public void delete() throws Exception {
        JdbcContactAddressDao addressDao = new JdbcContactAddressDao();
        addressDao.delete(4);
        assertTrue(null == addressDao.get(4));
    }
}