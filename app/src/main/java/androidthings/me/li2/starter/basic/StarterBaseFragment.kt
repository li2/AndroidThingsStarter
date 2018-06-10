package androidthings.me.li2.starter.basic

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View

/**
 * @author Weiyi Li on 10/6/18 | https://github.com/li2
 */
abstract class StarterBaseFragment: Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getView().isFocusableInTouchMode = true
        getView().requestFocus()
        getView().setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
            Log.i(tag, "keyCode: $keyCode")
            return@OnKeyListener onKey(view, keyCode, event)
        })
    }

    open fun onKey(view: View, keyCode: Int, event: KeyEvent): Boolean {
        return false
    }
}
