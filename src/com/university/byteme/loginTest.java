package com.university.byteme;

import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class loginTest {
    @BeforeClass
    public static void setup(){
        Main.customers.add(new Customer("abc", "abc", "vip" ));
        Main.customers.add(new Customer("def", "def", "vip" ));
        Main.customers.add(new Customer("raghav", "raghav", "regular" ));
    }

    @Test
    public void customerLogin_NullEmail() {
        String input = null;
        String result = Main.loginCheck(input,"abc");
        assertEquals("Invalid", result);
    }

    @Test
    public void customerLogin_ValidEmail() {
        String input = "abc";
        String result = Main.loginCheck(input,"abc");
        assertEquals("Valid", result);
    }

    @Test
    public void customerLogin_InvalidEmail() {
        String input = "abc";
        String result = Main.loginCheck(input,"raghav");
        assertEquals("Invalid", result);
    }

    @Test
    public void adminLogin_ValidEmail() {
        String input = "mess";
        String result = Main.loginCheckForAdmin(input,"mess");
        assertEquals("Valid", result);
    }

    @Test
    public void adminLogin_InvalidEmail() {
        String input = "admin";
        String result = Main.loginCheckForAdmin(input,"mess");
        assertEquals("Invalid", result);
    }

}
