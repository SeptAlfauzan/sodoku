package com.septalfauzan.sudoku.helper

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class TimerTest {
    @Test
    fun should_return_00_00_when_timeInSecond_is_0() {
        // Arrange
        val timeInSecond = 0

        // Act
        val result = formatTimer(timeInSecond)

        // Assert
        assertEquals("00:00", result)
    }

    @Test
    fun should_return_00_59_when_timeInSecond_is_59() {
        // Arrange
        val timeInSecond = 59

        // Act
        val result = formatTimer(timeInSecond)

        // Assert
        assertEquals("00:59", result)
    }

    @Test
    fun should_return_02_04_when_timeInSecond_is_124() {
        // Arrange
        val timeInSecond = 124

        // Act
        val result = formatTimer(timeInSecond)

        // Assert
        assertEquals("02:04", result)
    }
}
