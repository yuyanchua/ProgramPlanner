package com.MyApplicationTest;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.element.User;

/**
 * Tests the user class.
 * Currently just a constructor test.
 * ~/app/src/main/java/com/example/myapplication/User.java
 */
public class UserTest {
    /*
     * <Constructor Tests>
     * The following tests all test one aspect of the constructor.
     * They are very simple in nature, and merely assert that the
     * values of the created object reflect those passed in the constructor.
     * One test for each field.
     */

    @Test
    public void testConstructorUsername() {
        User testUser =
                new User("test name", "blank", 0, "blank");
        assertTrue("User constructor failed to properly initialize the 'username' field.", testUser.username.equals("test name"));
    }

    @Test
    public void testConstructorPassword() {
        User testUser =
                new User("blank", "test pass", 0, "blank");
        assertTrue("User constructor failed to properly initialize the 'password' field.", testUser.password.equals("test pass"));
    }

    @Test
    public void testConstructorQuestionIndex() {
        User testUser =
                new User("blank", "blank", 1, "blank");
        assertTrue("User constructor failed to properly initialize the 'questionIndex field.", testUser.questionIndex == 1);
    }

    @Test
    public void testConstructorAnswer() {
        User testUser =
                new User("blank", "blank", 0, "test answer");
        assertTrue("User constructor failed to properly initialize the 'answer' field.", testUser.answer.equals("test answer"));
    }
    //</Constructor Tests>
}