package androidthings.me.li2.starter.rainbow

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidthings.me.li2.starter.R
import androidthings.me.li2.starter.basic.DescriptionFragment
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManager
import java.io.IOException


private const val TAG = "ButtonInputLedOutputFragment"
// Button A
private const val BUTTON_A_PIN_NAME = "GPIO6_IO14"
// Button B
private const val BUTTON_B_PIN_NAME = "GPIO6_IO15"
// Red Led
private const val LED_PIN_NAME = "GPIO2_IO02"

class ButtonInputLedOutputFragment : DescriptionFragment() {

    // Driver for the GPIO button
    private lateinit var mButtonBInputDriver: ButtonInputDriver
    // GPIO connection to button input
    private lateinit var mButtonAGpio: Gpio
    // GPIO connection to led output
    private lateinit var mLedRedGpio: Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initHardware()
        registerButtonInputDriver()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseHardware()
        unregisterButtonInputDriver();
    }

    override fun getTitle(): String {
        return getString(R.string.ButtonInputLedOutputFragmentTitle)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Process GPIO by PeripheralManager
    ///////////////////////////////////////////////////////////////////////////

    private fun initHardware() {
        // list available peripherals
        var pioManager = PeripheralManager.getInstance()
        Log.d(TAG, "Available GPIO: " + pioManager.gpioList)

        // register a button input
        try {
            mButtonAGpio = pioManager.openGpio(BUTTON_A_PIN_NAME)
            // configure as an input
            mButtonAGpio.setDirection(Gpio.DIRECTION_IN)
            mButtonAGpio.setEdgeTriggerType(Gpio.EDGE_BOTH)
            mButtonAGpio.setActiveType(Gpio.ACTIVE_LOW)
            mButtonAGpio.registerGpioCallback(mButtonCallback)
        } catch (e : IOException) {
            Log.e(TAG, "Error opening GPIO", e)
        }

        // register a led output
        try {
            mLedRedGpio = pioManager.openGpio(LED_PIN_NAME)
            // configure as an output
            mLedRedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        } catch (e : IOException) {
            Log.e(TAG, "Error opening GPIO", e)
        }
    }

    private fun releaseHardware() {
        // close the button
        if (mButtonAGpio != null) {
            mButtonAGpio.unregisterGpioCallback(mButtonCallback)
            try {
                mButtonAGpio.close()
            } catch (e : IOException) {
                Log.e(TAG, "Error closing GPIO", e)
            }
        }

        // close the led
        if (mLedRedGpio != null) {
            try {
                mLedRedGpio.close()
            } catch (e : IOException) {
                Log.e(TAG, "Error closing GPIO", e)
            }
        }
    }

    private var mButtonCallback = GpioCallback { gpio ->
        try {
            Log.i(TAG, "GPIO changed, button " + gpio.value)
            setLedValue(gpio.value)
        } catch (e: IOException) {
            Log.e(TAG, "Error reading GPIO", e)
        }
        // return true to keep callback active
        true
    }

    private fun setLedValue(value: Boolean) {
        try {
            mLedRedGpio.value = value
        } catch (e: IOException) {
            Log.e(TAG, "Error updating GPIO", e)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Process GPIO by registering user space driver
    ///////////////////////////////////////////////////////////////////////////

    private fun registerButtonInputDriver() {
        try {
            // Initialize button driver to emit SPACE key events
            mButtonBInputDriver = ButtonInputDriver(
                    BUTTON_B_PIN_NAME,
                    Button.LogicState.PRESSED_WHEN_LOW,
                    KeyEvent.KEYCODE_SPACE)
            // Register with the framework
            mButtonBInputDriver.register()
        } catch (e: IOException) {
            Log.e(TAG, "Error opening button driver", e)
        }
    }

    private fun unregisterButtonInputDriver() {
        if (mButtonBInputDriver != null) {
            mButtonBInputDriver.unregister()
            try {
                mButtonBInputDriver.close()
            } catch (e: IOException) {
                Log.e(TAG, "Error closing Button driver", e)
            }
        }
    }

    // The button driver implements an input user driver to bind button transitions to key events in the Android framework.
    override fun onKey(view: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            setLedValue(event.action == KeyEvent.ACTION_DOWN)
            return true
        }
        return false
    }
}
