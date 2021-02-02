package vch.proj.ui.activities

import androidx.fragment.app.Fragment
import vch.proj.ui.fragments.WelcomeFragment

/**
 * Start Page Activity (can be used for login)
 */
class WelcomeActivity : BaseActivity() {
    override fun setFragment() = WelcomeFragment()
    override fun init() {
        super.init()
    }
}