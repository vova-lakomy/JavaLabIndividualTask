package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactDao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        System.out.println(contactDao.get(2));
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}