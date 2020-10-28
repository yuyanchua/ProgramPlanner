package com.MyApplicationTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.Project;

/**
 * Tests all methods in the Project class
 * See test comments for more details
 * ~/app/src/main/java/com/example/myapplication/Project.java
 */
public class ProjectTest {
    /*
     * <Constructor Tests>
     * The following tests all test one aspect of the constructor.
     * They are very simple in nature, and merely assert that the
     * values of the created object reflect those passed in the constructor.
     * One test for each field.
     */

    @Test
    public void testConstructorProjectId() {
        Project testProject = new Project(5, "blank", "blank", "blank");
        Project testProject2 = new Project(4, "blank", "blank", "blank");
        assertEquals("Project constructor failed to initialize the 'projectId' field.", 5, testProject.getProjectId());
    }

    //</Constructor Tests>
}
