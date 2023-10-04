package com.septalfauzan.sodoku.data.local

import android.content.Context
import com.septalfauzan.sodoku.core.data.local.LoadLocalJson
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
//        `when`(LoadLocalJson.getBoard(mockContext))
    }

    @Test
    fun getBoard() {
        val board = LoadLocalJson.getBoard(mockContext)
        println(board)
//        assertEquals(BoardResponse::class.java, board)
    }
}