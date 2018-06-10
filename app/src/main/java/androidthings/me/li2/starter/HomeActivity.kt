package androidthings.me.li2.starter

import android.os.Bundle
import androidthings.me.li2.starter.basic.StarterBaseActivity
import androidthings.me.li2.starter.rainbow.DriverUseCasesFragment

class HomeActivity : StarterBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, DriverUseCasesFragment())
                .commit()
    }
}
