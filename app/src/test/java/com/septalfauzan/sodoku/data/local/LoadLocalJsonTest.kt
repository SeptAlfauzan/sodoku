package com.septalfauzan.sodoku.data.local

import android.content.Context
import com.septalfauzan.sodoku.core.data.local.LoadLocalJson
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
internal class LoadLocalJsonTest {

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        mockContext = mock(Context::class.java)
    }

    @Test
    fun get_valid_board() {
        val jsonStr = """
            {
              "board": [
                [0,0,0, 0,0,0, 0,0,0],
                [0,0,0, 0,0,0, 0,0,0],
                [0,0,0, 0,0,0, 0,0,0],

                [0,0,0, 0,0,0, 0,0,0],
                [0,0,0, 0,0,0, 0,0,0],
                [0,0,0, 0,0,0, 0,0,0],

                [0,0,0, 0,0,0, 0,0,0],
                [0,0,0, 0,0,0, 0,0,0],
                [0,0,0, 0,0,0, 0,0,0]
              ]
            }
        """.trimIndent()
        val board = LoadLocalJson.getBoard(jsonStr)
        assertNotNull(board)
    }

    @Test
    fun get_invalid_board() {
        val jsonStr = """
            {}
        """.trimIndent()
        val boardResponse = LoadLocalJson.getBoard(jsonStr)
        assertNull(boardResponse.board)
    }
}