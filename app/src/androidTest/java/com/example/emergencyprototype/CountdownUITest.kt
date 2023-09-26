package com.example.emergencyprototype

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.emergencyprototype.emergency.CountdownUi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountdownUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun enableAccessibilityCheck(){
        AccessibilityChecks.enable().setRunChecksFromRootView(true)
    }

    @Test
    fun countdownUiTest() {
        composeTestRule.setContent {
            CountdownUi {}
        }

        composeTestRule.onNode(hasText("Seconds"))
            .assertIsDisplayed()

    }
}