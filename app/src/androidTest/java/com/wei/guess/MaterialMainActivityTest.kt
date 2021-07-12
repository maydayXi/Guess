package com.wei.guess

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Declare for test case
@RunWith(AndroidJUnit4::class)
class MaterialMainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MaterialMainActivity::class.java)

    // <summary> Test Input Unit Components </summary>
    @Test // Declare for test
    fun guessWrong() {
        val testInput = "1234"
        var answer = ""
        var output = ""

        // 測試文字輸入功能
        onView(withId(R.id.edtInput)).perform(clearText())
        onView(withId(R.id.edtInput)).perform(typeText(testInput))

        // 產生驗證字串
        activityRule.scenario.onActivity {
            answer = it.secretNumber.answer
            output = it.secretNumber.validate()
        }

        // 測試驗證按鈕功能
        onView(withId(R.id.btnValidate)).perform(click())

        // 檢查指定的文字有沒有出現在畫面上
        onView(withText(output)).check(matches(isDisplayed()))
    }
}