package androidthings.me.li2.starter.basic

import android.app.Activity
import android.os.Bundle

/**
 * @author Weiyi Li on 10/6/18 | https://github.com/li2
 */
abstract class StarterBaseActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}
