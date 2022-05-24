package com.example.se_project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import androidx.test.core.app.ApplicationProvider;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LoginUnitTest {


    private static final String FAKE_STRING = "조건 만족";

    UserSignupActivity signup;
    Context context;
    @Before
    public void setUp(){
        context = ApplicationProvider.getApplicationContext();

    }

    @Test
    public void readStringFromContext(){

        signup = new UserSignupActivity(context);
        String result = signup.CheckSignUpCondition("t", "t", "t", "t");
        assertEquals(result,FAKE_STRING);

    }
}