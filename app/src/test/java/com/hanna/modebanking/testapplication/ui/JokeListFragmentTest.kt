package com.hanna.modebanking.testapplication.ui

import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.hanna.modebanking.testapplication.R
import com.hanna.modebanking.testapplication.datasource.network.Resource
import com.hanna.modebanking.testapplication.extensions.replace
import com.hanna.modebanking.testapplication.model.Joke
import com.hanna.modebanking.testapplication.viewmodel.JokesViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class JokeListFragmentTest {

    lateinit var scenario: FragmentScenario<JokesListFragment>
    lateinit var factory: JokeListFragmentTestFactory
    val initListResource = Resource.success(listOf(Joke(joke = "This is the joke content")))
    val listValue = MutableStateFlow<Resource<List<Joke>>>(initListResource)
    private val viewModelMock: JokesViewModel = mock {
        onBlocking { jokesList() } doReturn listValue
    }

    private var clickCounter = 0

    @Before
    fun setupScenario() {
        clickCounter = 0
        factory = JokeListFragmentTestFactory(viewModelMock)
        scenario = FragmentScenario.launchInContainer(
            JokesListFragment::class.java,
            null, R.style.Theme_AppCompat_NoActionBar, factory
        )
    }

    @Test
    fun `when list successfully received with data, list length is same as data length`() {
        listValue.value = Resource.success(listOf(Joke(joke = "This is the joke content")))
        scenario.onFragment {
            val rv = it.view!!.findViewById<RecyclerView>(R.id.jokes_list)
            assertThat(rv!!.adapter?.itemCount).isEqualTo(1)
        }
    }

    @Test
    fun `when successful resource received, main_view is visible and loading view is gone`() {
        listValue.value = Resource.success(listOf(Joke(joke = "This is the joke content")))
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.main_view)
            val loadingView = it.view!!.findViewById<View>(R.id.loading_view)
            assertThat(mainView.isVisible).isTrue()
            assertThat(loadingView.isVisible).isFalse()
        }
    }

    @Test
    fun `when resource loading with no data, main_view is invisible and loading view is visible`() {
        listValue.value = Resource.loading(null)
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.main_view)
            val loadingView = it.view!!.findViewById<View>(R.id.loading_view)
            assertThat(mainView.isVisible).isFalse()//.isTrue()
            assertThat(loadingView.isVisible).isTrue()
        }
    }

    @Test
    fun `when resource loading with data, main_view is visible and loading view is gone`() {
        listValue.value = Resource.loading(listOf(Joke(joke = "This is the joke content")))
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.main_view)
            val loadingView = it.view!!.findViewById<View>(R.id.loading_view)
            assertThat(mainView.isVisible).isTrue()
            assertThat(loadingView.isVisible).isFalse()
        }
    }
}

@ExperimentalCoroutinesApi
class JokeListFragmentTestFactory constructor(
    var viewModelMock: JokesViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        JokesListFragment().apply {
            replace(JokesListFragment::viewModel, viewModelMock)
        }
}