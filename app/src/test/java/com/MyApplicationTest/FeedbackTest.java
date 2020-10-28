package com.MyApplicationTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.Feedback;

/**
 * Tests the Feedback class.
 * Currently just constructor Test.
 * ~/app/src/main/java/com/example/myapplication/User.java
 */
public class FeedbackTest {
    /*
     * <Constructor Tests>
     * The following tests all test one aspect of the constructor.
     * They are very simple in nature, and merely assert that the
     * values of the created object reflect those passed in the constructor.
     * One test for each field.
     */
    @Test
    public void testConstructorProjectId() {
        Feedback testFeed = new Feedback(3, "blank", "blank");
        assertEquals("Feedback constructor failed to properly initialize the 'projectId field.", 3, testFeed.projectId);
    }

    @Test
    public void testConstructorUsername() {
        Feedback testFeed = new Feedback(0, "test username", "blank");
        assertTrue("Feedback constructor failed to properly initialize the 'username' field.", testFeed.username.equals("test username"));
    }

    @Test
    public void testConstructorComment() {
        Feedback testFeed = new Feedback(0, "blank", "test comment");
        assertTrue("Feedback constructor failed to properly initialize the 'comment' field.", testFeed.comment.equals("test comment"));
    }

    //</Constructor Tests>
}
