package com.MyApplicationTest.engine;

import org.junit.Test;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.Mockito;
import com.example.myapplication.activity.ForgetPassActivity;
import com.example.myapplication.activity.ForgetQuesActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests the ForgetPass class.
 * Some tests will be a bit higher level
 * due to the integrated nature of this class
 */
public class ForgetPassTest {
    @Mock
    FirebaseDatabase databaseMock;

    @Mock
    DatabaseReference referenceMock;

    @Before
    public void setUp() {
        databaseMock = Mockito.mock(FirebaseDatabase.class);
        referenceMock = Mockito.mock(DatabaseReference.class);
        when(databaseMock.getReference(anyString())).thenReturn(referenceMock);
    }
    //<constructor tests>

    @Test
    public void testForgPassConstructor() {

    }

    //</constructor tests?

}
