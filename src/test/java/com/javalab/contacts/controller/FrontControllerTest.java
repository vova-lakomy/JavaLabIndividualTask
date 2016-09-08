package com.javalab.contacts.controller;


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
        FrontController controller = new FrontController();
        assertTrue(controller.getAllContacts().size() == 10);
    }

}