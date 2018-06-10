package androidthings.me.li2.starter.rainbow

import android.os.Bundle
import android.util.Log
import androidthings.me.li2.starter.R
import androidthings.me.li2.starter.basic.DescriptionFragment
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManager
import java.io.IOException

private const val TAG = "ButtonInputLedOutputFragment"
// Button A
private const val BUTTON_PIN_NAME = "GPIO6_IO14"
// Red Led
private const val LED_PIN_NAME = "GPIO2_IO02"

class ButtonInputLedOutputFragment : DescriptionFragment() {

    // GPIO connection to button input
    private lateinit var mButtonGpio: Gpio
    // GPIO connection to led output
    private lateinit var mLedGpio: Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initHardware()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseHardware()
    }

    override fun getTitle(): String {
        return getString(R.string.ButtonInputLedOutputFragmentTitle)
    }

    private fun initHardware() {
        // list available peripherals
        var pioManager = PeripheralManager.getInstance()
        Log.d(TAG, "Available GPIO: " + pioManager.gpioList)

        // register a button input
        try {
            mButtonGpio = pioManager.openGpio(BUTTON_PIN_NAME)
            // configure as an input
            mButtonGpio.setDirection(Gpio.DIRECTION_IN)
            mButtonGpio.setEdgeTriggerType(Gpio.EDGE_BOTH)
            mButtonGpio.setActiveType(Gpio.ACTIVE_LOW)
            mButtonGpio.registerGpioCallback(mButtonCallback)
        } catch (e : IOException) {
            Log.e(TAG, "Error opening GPIO", e)
        }

        // register a led output
        try {
            mLedGpio = pioManager.openGpio(LED_PIN_NAME)
            // configure as an output
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        } catch (e : IOException) {
            Log.e(TAG, "Error opening GPIO", e)
        }
    }

    private fun releaseHardware() {
        // close the button
        if (mButtonGpio != null) {
            mButtonGpio.unregisterGpioCallback(mButtonCallback)
            try {
                mButtonGpio.close()
            } catch (e : IOException) {
                Log.e(TAG, "Error closing GPIO", e)
            }
        }

        // close the led
        if (mLedGpio != null) {
            try {
                mLedGpio.close()
            } catch (e : IOException) {
                Log.e(TAG, "Error closing GPIO", e)
            }
        }
    }

    private var mButtonCallback = GpioCallback { gpio ->
        try {
            Log.i(TAG, "GPIO changed, button " + gpio.value)
            mLedGpio.value = gpio.value
        } catch (e: IOException) {
            Log.e(TAG, "Error reading GPIO", e)
        }
        // return true to keep callback active
        true
    }
}
