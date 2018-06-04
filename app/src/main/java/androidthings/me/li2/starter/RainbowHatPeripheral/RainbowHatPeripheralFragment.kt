package androidthings.me.li2.starter.RainbowHatPeripheral

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidthings.me.li2.starter.R
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio


/**
 * Rainbow Hat Driver Use Cases.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>`val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
`</pre> *
 *
 *
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see [https://github.com/androidthings/contrib-drivers.readme](https://github.com/androidthings/contrib-drivers.readme)
 *
 * @see [https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
 */

class RainbowHatPeripheralFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPeripheral()
    }

    /*
     NOTE21: Caused by: java.lang.IllegalArgumentException: Parameter specified as non-null is null: method kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull, parameter savedInstanceState
     By default all variables and parameters in Kotlin are non-null. If you want to pass null parameter to the method you should add ?,
     for example: savedInstanceState: Bundle?
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.rainbow_hat_peripheral_fragment, container, false)

        rootView.findViewById<Switch>(R.id.red_led_switch).setOnCheckedChangeListener { _, isChecked ->  switchRedLed(isChecked)}

        return  rootView;
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePeripheral()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Peripheral
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private lateinit var redLed : Gpio

    private fun initPeripheral() {
        redLed = RainbowHat.openLedRed()
    }

    private fun releasePeripheral() {
        redLed.close()
    }

    /*
    NOTE21: Kotlin: fun, var, parameters
     */
    private fun switchRedLed(on: Boolean) {
        redLed.value = on
    }
}
