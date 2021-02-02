package vch.proj.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import vch.proj.R

/**
 * Main Abstract Activity
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var fragmentManager: FragmentManager = supportFragmentManager
    /**
     * Set Fragment - return fragment which will be set for activity
     * @return instance of @{{@link Fragment}}
     */
    protected abstract fun setFragment(): Fragment
    /**
     * Init - initialize additional settings for activity
     */
    open fun init(): Unit {
        supportActionBar?.hide()
    }
    /**
     * Get Layout - return base resource
     * @return resourse id
     */
    @SuppressLint("SupportAnnotationUsage")
    @LayoutRes
    protected fun getLayout(): Int = R.layout.main_frame_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        init()

        var fragment: Fragment? = fragmentManager.findFragmentById(R.id.main_container)

        if (null == fragment) {
            fragment = setFragment()
        }

        if (!fragment.isAdded) {
            changeFragment(fragment)
        }
    }

    /**
     * Change Fragment - function which set specified fragment
     * @param fragment instance of Fragment
     */
    protected fun changeFragment(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}