package androidthings.me.li2.starter

import android.app.Activity
import android.os.Bundle
import androidthings.me.li2.starter.RainbowHatPeripheral.RainbowHatPeripheralFragment

class HomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, RainbowHatPeripheralFragment())
                .commit()
    }
}
