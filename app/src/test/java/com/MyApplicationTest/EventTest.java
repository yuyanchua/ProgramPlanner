package com.MyApplicationTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.Event;

/**
 * Tests the Event class.
 * Tests for all current methods
 * ~/app/src/main/java/com/example/myapplication/Event.java
 */
public class EventTest {
    /*
     * <Constructor Tests>
     * The following tests all test one aspect of the constructor.
     * They are very simple in nature, and merely assert that the
     * values of the created object reflect those passed in the constructor.
     * One test for each field.
     */

    @Test
    public void testConstructorEventTitle() {
        Event testEvent = new Event("Test Title", "blank", false);
        assertTrue("Event constructor failed to initialize the 'eventTitle' field.", testEvent.eventTitle.equals("Test Title"));
    }

    @Test
    public void testConstructorEventDate() {
        Event testEvent = new Event("blank", "testDate", false);
        assertTrue("Event constructor failed to initialize the 'eventDate' field.", testEvent.eventDate.equals("testDate"));
    }

    @Test
    public void testConstructorIsNotify() {
        Event testEvent = new Event("blank", "blank", true);
        assertTrue("Event constructor failed to initialize the 'isNotify' field.", testEvent.isNotify);
    }

    //</Constructor Tests>


    /*
     * Test the equals function to verify
     * same date and different other fields is equal.
     *
    @Test
    public void testEqualsEqual() {
        Event testEvent = new Event("blank", "Date 1", false);
        Event testEvent1 = new Event("not blank", "Date 1", true);
        assertTrue("Event equals did not say events with the same date were equal.", testEvent.equals(testEvent1));
    }

    /**
     * Test the equals function to verify
     * different date and same other fields isn't equal.
     *
    @Test
    public void testEqualsNot() {
        Event testEvent = new Event("blank", "Date 1", false);
        Event testEvent1 = new Event("blank", "Date 2", false);
        assertFalse("Event equals did not say events with different date weren't equal.", testEvent.equals(testEvent1));
    }*/

    /**
     * Test the toString function.
     * Ensures created string is accurately formatted.
     */
    @Test
    public void testToString() {
        String correct = "Event Title: Test Title, Event Date: Test Date\n";
        Event testEvent = new Event("Test Title", "Test Date", false);
        assertTrue("Event.toString did not return correct string.", correct.equals(testEvent.toString()));
    }
}
