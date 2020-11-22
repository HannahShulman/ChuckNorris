package com.hanna.modebanking.testapplication.ui

import android.os.Build
import android.view.LayoutInflater
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.hanna.modebanking.testapplication.R
import com.hanna.modebanking.testapplication.model.Joke
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class JokesListAdapterTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val clickListener = { _: Int -> }
    val adapter = JokesListAdapter(clickListener)

    @Test
    fun `diffUtil when ids are the same, items return as same items`() {
        val isSame = JokesListAdapter.diffUtil.areItemsTheSame(Joke(), Joke())
        assertThat(isSame).isTrue()
    }

    @Test
    fun `diffUtil when ids are not the same, items return not as same items`() {
        val isSame = JokesListAdapter.diffUtil.areItemsTheSame(Joke(), Joke().copy(id = 3))
        assertThat(isSame).isFalse()
    }

    @Test
    fun `diffUtil when joke content is the same, areContentsTheSame return true`() {
        val isSame = JokesListAdapter.diffUtil.areContentsTheSame(Joke(), Joke())
        assertThat(isSame).isTrue()
    }

    @Test
    fun `diffUtil when joke content are not the same, areContentsTheSame return false`() {
        val isSame =
            JokesListAdapter.diffUtil.areContentsTheSame(Joke(), Joke().copy(joke = "content"))
        assertThat(isSame).isFalse()
    }

    @Test
    fun `view holder displays correct content`() {
        adapter.submitList(listOf(Joke(joke = "content")))
        val view = LayoutInflater.from(context).inflate(R.layout.single_joke_layout, null)
        val holder = JokeViewHolder(view, clickListener)
        adapter.onBindViewHolder(holder, 0)
        assertThat(holder.jokeTextView.text).isEqualTo("content")
    }

}