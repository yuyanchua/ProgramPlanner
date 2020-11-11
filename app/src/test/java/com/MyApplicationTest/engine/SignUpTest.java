package com.MyApplicationTest.engine;

import androidx.annotation.NonNull;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.example.myapplication.element.User;
import com.example.myapplication.activity.SignUpActivity;
import com.example.myapplication.engine.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Tests the SignUp class.
 * Methods will be tested in a somewhat integrated state
 * due to their closely tied functionality.
 * ~/app/src/main/java/com/example/myapplication/engine/SignUp.java
 */
public class SignUpTest {
    @Mock
    FirebaseDatabase databaseMock;

    @Mock
    DatabaseReference referenceMock;

    @Mock
    SignUpActivity signUpMock;

    //private boolean passFlag;

    //@InjectMocks
    //ignUp testSignUp;

    @Before
    public void setUp() {
        //passFlag = false;
        databaseMock = Mockito.mock(FirebaseDatabase.class);
        referenceMock = Mockito.mock(DatabaseReference.class);
        signUpMock = Mockito.mock(SignUpActivity.class);
        when(databaseMock.getReference(anyString())).thenReturn(referenceMock);

    }

    /**
     * Test constructor with null user password.
     * Should call signup and set the activity's ErrView
     * this should verify that activity and user are set by constructor,
     * as well as covering a branch of signup.
     */
    @Test
    public void testConstructorAndErrSet() {
        User testUser = new User("testName", null);
        //This static mocking technique found at
        //https://tech.cognifide.com/blog/2020/mocking-static-methods-made-possible-in-mockito-3.4.0/
        try (MockedStatic<FirebaseDatabase> mockFirebase = Mockito.mockStatic(FirebaseDatabase.class)) {
            mockFirebase.when(FirebaseDatabase::getInstance).thenReturn(databaseMock);
            SignUp testSignUp = new SignUp(signUpMock, testUser);
            System.out.println("***One java.lang.NullPointerException should be thrown from testConstructorAndErrSet***");
            //Verify that setErrView was called due to null password
            verify(signUpMock, times(1)).setErrView("Encountered unexpected error");
        }
    }

    /**
     * Test signup with a non null user password.
     * This will call updateDatabase.
     * Verify that the addListenerForSingleValueEvent is called
     */
    @Test
    public void testSignUpNotNull() {
        User testUser = new User("testName", "testPass");
        try (MockedStatic<FirebaseDatabase> mockFirebase = Mockito.mockStatic(FirebaseDatabase.class)) {
            mockFirebase.when(FirebaseDatabase::getInstance).thenReturn(databaseMock);
            SignUp testSignUp = new SignUp(signUpMock, testUser);
            verify(referenceMock, times(1)).addListenerForSingleValueEvent(any());
        }
    }

    /**
     * Test validatePassword.
     * Will be called
     *
     */

    /**
     * Test the listener onDataChange
     * will create a SignUp, then modify the user.
     * Verify that activity.finishSignUp is called
     *
    @Test
    public void testOnDataChangeNotExist() {
        User testUser = new User("testName", "testPass");
        try (MockedStatic<FirebaseDatabase> mockFirebase = Mockito.mockStatic(FirebaseDatabase.class)) {
            mockFirebase.when(FirebaseDatabase::getInstance).thenReturn(databaseMock);
            doAnswer( i -> new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    System.out.println("Here");
                    if(snapshot.child(testUser.username).exists()){
                        signUpMock.setErrView("Username is already exist in database");
                    }else{

                        referenceMock.child(testUser.username).setValue(testUser);
                        Toast.makeText(signUpMock.getApplicationContext(), "SignUp successfully", Toast.LENGTH_SHORT).show();
                        signUpMock.finishSignUp();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("Here");
                }
            }).when(referenceMock).addListenerForSingleValueEvent(any());

            SignUp testSignUp = new SignUp(signUpMock, testUser);
            testUser.username = "newName";
        }
    }*/
}
