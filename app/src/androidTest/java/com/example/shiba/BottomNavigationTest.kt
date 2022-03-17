package com.example.shiba

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class BottomNavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNavigationTest() {
        val tabItems = listOf(TabItem.Lists, TabItem.Check, TabItem.Register, TabItem.Statistics)
        var selectedTabIndex by mutableStateOf(tabItems.indexOf(TabItem.Lists))
        composeTestRule.setContent {
            BottomNavigation(
                tabItems = tabItems,
                selectedTabIndex = selectedTabIndex,
                onTabClick = { selectedTabIndex = it }
            )
        }
        // init
        composeTestRule.onNodeWithText(TabItem.Lists.name).assertIsSelected()

        //before
        composeTestRule.onNodeWithText(TabItem.Register.name).assertIsNotSelected()
        // action
        composeTestRule.onNodeWithText(TabItem.Register.name).performClick()
        // after
        composeTestRule.onNodeWithText(TabItem.Register.name).assertIsSelected()
    }
}
