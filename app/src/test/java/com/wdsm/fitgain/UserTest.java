package com.wdsm.fitgain;

import static org.junit.Assert.assertEquals;

import com.wdsm.fitgain.Entities.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void userTest() {
        User user = User.getInstance();
        user.setEMail("testMail");
        user.setLogin("testLogin");
        assertEquals("testLogin", user.getLogin());
        assertEquals("testMail", user.getEMail());
    }

}
