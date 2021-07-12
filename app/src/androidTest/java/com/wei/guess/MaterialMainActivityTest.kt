package com.wei.guess

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Declare for test case
@RunWith(AndroidJUnit4::class)
class MaterialMainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MaterialMainActivity::class.java)

    private lateinit var device: UiDevice

    // <summary> Test Input Unit Components </summary>
    @Test // Declare for test
    fun guessWrong() {
        val testInput = "1234"
        var answer = ""
        var output = ""

        // 測試文字輸入功能
        inputText(testInput)

        // 產生驗證字串
        activityRule.scenario.onActivity {
            answer = it.secretNumber.answer
            output = it.secretNumber.validate()
        }

        // 測試驗證按鈕功能
        buttonClick()

        // 檢查指定的文字有沒有出現在畫面上
        onView(withText(output)).check(matches(isDisplayed()))
    }

    // <summary> Replay Button Test </summary>
    @Test
    fun replayClick() {

        device = UiDevice.getInstance(InstrumentationRegistry
            .getInstrumentation())

        var test_count = 0
        var answer = ""

        // guess three times
        repeat((0..2).count()) {
            val test_guess = (0..9).toMutableList()
                .shuffled()
                .take(4)
                .joinToString("")

            inputText(test_guess)
            buttonClick()
            closeDialog()
        }

        // get answer
        activityRule.scenario.onActivity { answer = it.secretNumber.answer }

        // guess answer
        inputText(answer)
        buttonClick()
        closeDialog()

        // click replay button
        onView(withId(R.id.fabReplay)).perform(click())
        // click yes to replay
        onView(withText(R.string.dialog_btn_yes)).perform(click())
        // get replay guess count
        activityRule.scenario.onActivity { test_count = it.secretNumber.guess_cnt }
        // Compare count
        Assert.assertEquals(0, test_count)
    }

    // region private test method
    // <summary> Test type text </summary>
    private fun inputText(args: String) {
        onView(withId(R.id.edtInput)).perform(clearText())
        onView(withId(R.id.edtInput)).perform(typeText(args))
    }

    // <summary> Test button click </summary>
    private fun buttonClick() {
        onView(withId(R.id.btnValidate)).perform(click())
    }

    // <summary> Close Dialog </summary>
    private fun closeDialog() {
        device.click(100, 100)
    }
    // endregion
}