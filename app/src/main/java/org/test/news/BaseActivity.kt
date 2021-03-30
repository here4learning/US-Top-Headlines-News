package org.test.news

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager

abstract class BaseActivity : AppCompatActivity() {

    var mainToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //remove everything from back-stack. For some phone if screen is in background again new creation of activity will be occurred
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }

    fun setActionBar(resId: Int) {
        mainToolbar = findViewById(resId)
        mainToolbar?.let { toolbar ->
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            toolbar.setNavigationOnClickListener {
                val fragment = supportFragmentManager.findFragmentByTag(
                    (supportFragmentManager.backStackEntryCount - 1).toString()
                )
                if (fragment is BaseFragment) {
                    fragment.onHomeButtonPress()
                }
                onBackPressed()
            }
        }
    }

    fun setIcon(res: Int) {
        mainToolbar?.setNavigationIcon(res)
    }

    fun clearNavigationIcon() {
        mainToolbar?.navigationIcon = null
    }

    fun setTitle(title: String) {
        val actionBar = supportActionBar
        actionBar?.run {
            this.title = title
            setSubTitle("")
            setDisplayShowTitleEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun setTitle(resId: Int) {
        setTitle(getString(resId))
    }

    fun setSubTitle(title: String) {
        supportActionBar?.subtitle = title
    }

    fun hideMainToolbar() {
        supportActionBar?.hide()
    }

    fun showMainToolbar() {
        // We have to shevery time because hide is actually will remove the Toolbar.
        setSupportActionBar(mainToolbar)
        supportActionBar?.run {
            show()
            setBackEnabled()
        }
    }

    fun showMainToolbar(resource: String?) {
        // We have to every time because hide is actually will remove the Toolbar.
        setSupportActionBar(mainToolbar)
        supportActionBar?.run {
            show()
            setBackEnabled()
            title = resource
            setDisplayShowTitleEnabled(true)
            setSubTitle("")
        }
    }

    fun showMainToolbar(resourceId: Int) {
        showMainToolbar(getString(resourceId))
    }

    /**
     * Method for adding fragment
     *
     * @param containerId Container of the fragment
     * @param fragment    The fragment to be added
     */
    @JvmOverloads
    fun add(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean = true) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(containerId, fragment, Integer.toString(supportFragmentManager.backStackEntryCount))
        if (addToBackStack) ft.addToBackStack(null)
        ft.commit()
    }

    /**
     * Method for replacing fragment
     *
     * @param containerId Container of the fragment
     * @param fragment    The fragment to place in the container
     */
    @JvmOverloads
    fun replace(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean = true) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(containerId, fragment, Integer.toString(supportFragmentManager.backStackEntryCount))
        if (addToBackStack) ft.addToBackStack(null)
        ft.commit()
        fm.executePendingTransactions()

    }

    fun pop() {
        val fm = supportFragmentManager
        fm.popBackStackImmediate()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            supportFragmentManager.findFragmentByTag((count - 1).toString())?.run {
                (this as BaseFragment).onBackPress()
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                val fragment = supportFragmentManager.findFragmentByTag(
                    (supportFragmentManager.backStackEntryCount - 1).toString()
                )
                if (fragment is BaseFragment) {
                    fragment.onHomeButtonPress()
                }
                if (supportFragmentManager.backStackEntryCount > 1) {
                    onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    fun setBackEnabled() {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    fun setBackDisabled() {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val count = supportFragmentManager.backStackEntryCount
        if (count > 0) {
            supportFragmentManager.findFragmentByTag((count - 1).toString())?.apply {
                var intent = Intent()
                data?.run { intent = this }
                this.onActivityResult(requestCode, resultCode, intent)
            }
        }
    }

}
