package com.deonico.footballapp_kade

import org.junit.Test

import org.junit.Assert.*

import java.text.SimpleDateFormat

class FuncKtTest {

    //Cek convert format Date
    @Test
    fun changeFormatDate() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/26/2018")
        assertEquals("Sen, 26 Feb 2018", changeFormatDate(date))
    }
}