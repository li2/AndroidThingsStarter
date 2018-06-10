package androidthings.me.li2.starter.basic


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidthings.me.li2.starter.R

/**
 * A basic fragment only with a TextView to show description of the purpose of fragment.
 */
abstract class DescriptionFragment: Fragment() {

    private lateinit var mTitleView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.description_fragment, container, false)
        mTitleView = view.findViewById(R.id.descriptionTextView)
        mTitleView.text = getTitle()
        return view
    }

    abstract fun getTitle(): String
}
