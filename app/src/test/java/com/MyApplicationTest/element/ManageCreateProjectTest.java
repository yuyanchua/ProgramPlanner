package com.MyApplicationTest.element;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.element.ManageCreateProject;

/**
 * Tests the Log class.
 * Currently just constructor Test.
 * Following November 3rd commit.
 * May need to add more or remove later.
 * ~/app/src/main/java/com/example/myapplication/ManageCreateProject.java
 */
public class ManageCreateProjectTest {
    //<constructor test>
    @Test
    public void testBlankConstructor(){
        ManageCreateProject testMCP = new ManageCreateProject();
        assertNotNull("MCP was not created by blank constructor.",
                testMCP);
    }
}
