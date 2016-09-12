package com.javalab.contacts.service;


import com.javalab.contacts.dto.DtoRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.javalab.contacts.util.SqlScriptLoader.loadScript;
import static org.junit.Assert.*;


public class FrontControllerTest {
    @BeforeClass
    public static void setUp() throws Exception {
        loadScript("src/main/resources/initDB.sql");
        loadScript("src/main/resources/populateDB.sql");
    }

    @Test
    public void getAllContacts() throws Exception {
        DtoRepository controller = new DtoRepository();
        assertTrue(controller.getContactsList().size() == 10);
    }

}