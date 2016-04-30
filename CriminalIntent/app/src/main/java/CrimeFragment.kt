import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.stefan.criminalintent.Crime
import com.example.stefan.criminalintent.R
import org.jetbrains.anko.editText
import org.jetbrains.anko.hintResource
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.support.v4.UI

/**
 * Created by stefan on 15.04.16.
 */

class CrimeFragment: Fragment() {
    private val mCrime = Crime()
    private var mTitleField: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    //override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup, savedInstanceState: Bundle?): View? {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        val ui =  UI{
            linearLayout{
                mTitleField = editText{
                    hintResource = R.string.crime_title_hint
                }
            }
        }.view

        mTitleField?.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        return ui
    }
}