package com.hanna.modebanking.testapplication.converters

import com.google.common.truth.Truth.assertThat
import com.hanna.modebanking.testapplication.datasource.db.CategoriesConverter
import org.junit.Test

class CategoriesConverterTest {

    @Test
    fun `convert string to array`(){
        val string = CategoriesConverter().arrayToString(listOf("JOKE1", "JOKE2"))
        assertThat(string).isEqualTo("[\"JOKE1\",\"JOKE2\"]")
    }

    @Test
    fun `convert array to string`(){
        val string = CategoriesConverter().stringToCategoriesArray("[\"JOKE1\",\"JOKE2\"]")
        assertThat(string).isEqualTo(listOf("JOKE1", "JOKE2"))
    }
}