package com.apboutos.moneytrack.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.apboutos.moneytrack.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun test_isActivityInView(){

        onView(withId(R.id.activity_login)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isLoginLogoBoxProperlyDisplayed(){

        onView(withId(R.id.activity_login_logoBox)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_login_logoBox)).check(matches(withText(R.string.activity_login_Logo)))
    }

    @Test
    fun test_isLoginUsernameBoxProperlyDisplayed(){

        onView(withId(R.id.activity_login_usernameBox)).check(matches(isDisplayed()))

    }

    @Test
    fun test_isLoginPasswordBoxProperlyDisplayed(){

        onView(withId(R.id.activity_login_passwordBox)).check(matches(isDisplayed()))

    }

    @Test
    fun test_isLoginRememberMeBoxProperlyDisplayed(){

        onView(withId(R.id.activity_login_rememberMeBox)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_login_rememberMeBox)).check(matches(withText(R.string.activity_login_remember_me)))

    }

    @Test
    fun test_isLoginForgotTextProperlyDisplayed(){

        onView(withId(R.id.activity_login_forgotText)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.activity_login_forgotText)).check(matches(withText(R.string.activity_login_retrieve_credentials)))

    }

    @Test
    fun test_isLoginButtonProperlyDisplayed(){

        onView(withId(R.id.activity_login_loginButton)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_login_loginButton)).check(matches(withText(R.string.activity_login_Login_Button_Text)))

    }

    @Test
    fun test_isSignUpTextProperlyDisplayed(){

        onView(withId(R.id.activity_login_signUpText)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_login_signUpText)).check(matches(withText(R.string.activity_login_register_prompt)))

    }

    @Test
    fun test_isProgressBarProperlyDisplayed(){

        onView(withId(R.id.activity_login_progressBar)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))

    }

    @Test
    fun fillUsernameAndPasswordBoxes(){


    }
}