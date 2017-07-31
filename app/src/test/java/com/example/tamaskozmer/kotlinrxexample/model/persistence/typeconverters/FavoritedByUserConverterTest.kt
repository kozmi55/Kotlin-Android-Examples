package com.example.tamaskozmer.kotlinrxexample.model.persistence.typeconverters

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Tamas_Kozmer on 7/27/2017.
 */
class FavoritedByUserConverterTest {

    lateinit var favoritedByUserConverter: FavoritedByUserConverter

    @Before
    fun setUp() {
        favoritedByUserConverter = FavoritedByUserConverter()
    }

    @Test
    fun testFromString_emptyString_returnEmptyList() {
        val result = favoritedByUserConverter.fromString("")
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun testFromString_validString_listContainsNumbersFromString() {
        val result = favoritedByUserConverter.fromString("123;1234;12345")
        Assert.assertEquals(3, result.size)
        Assert.assertEquals(123L, result[0])
        Assert.assertEquals(1234L, result[1])
        Assert.assertEquals(12345L, result[2])
    }

    @Test
    fun testToString_emptyList_returnEmptyString() {
        val result = favoritedByUserConverter.toString(emptyList())
        Assert.assertEquals("", result)
    }

    @Test
    fun testToString_notEmptyList_stringIsCorrect() {
        val result = favoritedByUserConverter.toString(listOf(123L,1234L))
        Assert.assertEquals("123;1234", result)
    }
}