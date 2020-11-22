package com.hanna.modebanking.testapplication.ui

import android.os.Build
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.hanna.modebanking.testapplication.R
import com.hanna.modebanking.testapplication.extensions.replace
import com.hanna.modebanking.testapplication.model.Joke
import com.hanna.modebanking.testapplication.viewmodel.JokesViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class JokeDialogTest {

    lateinit var scenario: FragmentScenario<JokeDialog>
    lateinit var factory: JokeDialogTestFactory
    private val viewModelMock: JokesViewModel = mock<JokesViewModel> {
        onBlocking { getJokeById(4) } doReturn Joke(joke = "This is the joke content")
    }

    @Before
    fun setupScenario() {
        factory = JokeDialogTestFactory(viewModelMock)
        scenario = FragmentScenario.launchInContainer(
            JokeDialog::class.java,
            bundleOf(JokeDialog.JOKE_ID to 4), R.style.Theme_AppCompat_NoActionBar, factory
        )
    }

    @Test
    fun `creating fragment by newInstance, passes value to argument with joke_id key`() {
        val fragment = JokeDialog.newInstance(4)
        val arguments = fragment.arguments
        assertThat(arguments?.get("joke_id")).isEqualTo(4)
    }

    @Test
    fun `joke dialog displays as title the right joke number`() {
        runBlockingTest {
            scenario.onFragment {
                assertDisplayed(R.id.title, "#4")
            }
        }
    }

    @Test
    fun `joke dialog displays the joke content as the dialog body`() {
        runBlockingTest {
            scenario.onFragment {
                assertDisplayed(R.id.body, "This is the joke content")
            }
        }
    }

    @Test
    fun `joke dialog button clicked, dismisses dialog`() {
        runBlockingTest {
            scenario.onFragment {
                clickOn(R.id.primary_button)
                assertThat(it.isVisible).isFalse()
            }
        }
    }
}

@ExperimentalCoroutinesApi
class JokeDialogTestFactory constructor(
    var viewModelMock: JokesViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        JokeDialog().apply {
            replace(JokeDialog::viewModel, viewModelMock)
        }
}