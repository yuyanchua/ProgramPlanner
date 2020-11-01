package com.MyApplicationTest;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.example.myapplication.element.Task;

/**
 * Tests the Task class.
 * Currently just constructor Test.
 * ~/app/src/main/java/com/example/myapplication/Roles.java
 */
public class TaskTest {
    /*
     * <Constructor Tests>
     * The following tests all test one aspect of the constructor.
     * They are very simple in nature, and merely assert that the
     * values of the created object reflect those passed in the constructor.
     * One test for each field.
     */

    @Test
    public void test1ParamConstructorTask() {
        Task testTask = new Task("Test task");
        assertTrue("Single parameter Task constructor failed to initialize 'task' field.",
               testTask.task.equals("Test task"));
    }

    @Test
    public void test1ParamConstructorList() {
        Task testTask = new Task("Test task");
        assertTrue("Single parameter Task constructor failed to create empty list for 'memberList' field.",
                testTask.memberList.isEmpty());
    }

    @Test
    public void test2ParamConstructorTask() {
        Task testTask = new Task("Test task", new ArrayList<String>());
        assertTrue("2 parameter Task constructor failed to initialize 'task' field.",
                testTask.task.equals("Test task"));
    }

    @Test
    public void test2ParamConstructorList(){
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("test 1");
        Task testTask = new Task("Test task", testList);
        assertTrue("2 parameter Task constructor failed to use list for 'memberList' field.",
                testTask.memberList.get(0).equals("test 1"));
    }

    //</Constructor Tests>
}
