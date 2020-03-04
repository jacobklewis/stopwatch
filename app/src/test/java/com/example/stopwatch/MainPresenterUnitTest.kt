package com.example.stopwatch

import com.example.stopwatch.main.MainPresenter
import com.example.stopwatch.main.MainView
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainPresenterUnitTest {

    @MockK(relaxed = true)
    lateinit var mainView: MainView

    lateinit var presenter: MainPresenter

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun testToggleStartOn() {
        presenter = MainPresenter()
        presenter.attach(mainView)

        // Toggle On
        presenter.toggleStart(100)
        verify {
            mainView.updateStartButtonText(R.string.label_stop)
            mainView.updateResetButtonVisibility(true)
        }
        confirmVerified(mainView)
    }

    @Test
    fun testToggleStartOff() {
        // Toggle On
        testToggleStartOn()

        // Toggle Off
        presenter.toggleStart(200)
        verify {
            mainView.updateStartButtonText(R.string.label_start)
        }
        confirmVerified(mainView)
    }

    @Test
    fun testReset() {
        // Toggle On
        testToggleStartOn()

        // Toggle Off
        presenter.reset(200)
        verify {
            mainView.updateStartButtonText(R.string.label_start)
            mainView.updateTimeText("00:00.000")
            mainView.updateResetButtonVisibility(false)
        }
        confirmVerified(mainView)
    }

    @Test
    fun testUpdate() {
        // Toggle On
        testToggleStartOn()

        // Toggle Off
        presenter.onUpdate(0, 200)
        verify {
            mainView.updateTimeText("00:00.100")
        }
        confirmVerified(mainView)
    }

    @Test
    fun testUpdateSec() {
        // Toggle On
        testToggleStartOn()

        // Toggle Off
        presenter.onUpdate(0, 1105)
        verify {
            mainView.updateTimeText("00:01.005")
        }
        confirmVerified(mainView)
    }

    @Test
    fun testUpdateMin() {
        // Toggle On
        testToggleStartOn()

        // Toggle Off
        presenter.onUpdate(0, 62115)
        verify {
            mainView.updateTimeText("01:02.015")
        }
        confirmVerified(mainView)
    }
}
