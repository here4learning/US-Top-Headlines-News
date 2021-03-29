package org.test.news

import android.content.BroadcastReceiver
import android.os.Bundle
import android.text.SpannableString
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.reactivex.disposables.CompositeDisposable
import org.test.news.extension.gone
import org.test.news.extension.visibility
import org.test.news.widget.temptyview.TEmptyView


abstract class BaseFragment : DialogFragment() {

    private var mOnItemClickListener : OnItemClickListener<Int>? = null

    val toolbar: Toolbar?
        get() {
            var toolbar: Toolbar? = null
            activity?.run {
                toolbar = (this as BaseActivity).mainToolbar
            }
            return toolbar
        }

    val isDialog: Boolean
        get() = dialog != null

    protected fun setNoTitle() {
        if (isDialog) {
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        } else {
            activity?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

    fun setTitle(resMessage: Int){
        with(activity as BaseActivity){
            setTitle(resMessage)
            setBackDisabled()
        }
    }

    fun setTitle(message: String?){
        with(activity as BaseActivity){
            setTitle(message)
            setBackDisabled()
        }
    }

    fun setTitleWithBackButton(resMessage: Int){
        (activity as BaseActivity).showMainToolbar(resMessage)
    }

    fun setTitleWithBackButton(message: String?){
        (activity as BaseActivity).showMainToolbar(message)
    }

    fun setOnItemClickListener(listener: OnItemClickListener<Int>?){
        mOnItemClickListener = listener
    }

    fun hideToolbar() {
        activity?.run {
            (this as BaseActivity).hideMainToolbar()
        }
    }

    protected fun doRefresh(notificationType: Int) {
        //empty code
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onResume() {
        super.onResume()
        view?.run {
            isClickable = true
            setBackgroundColor()
        }
    }

    private fun setBackgroundColor() {
        context?.let { context ->
            view?.setBackgroundColor(ContextCompat.getColor(context, backgroundColor()))
        }
    }

    protected open fun backgroundColor(): Int {
        return R.color.color_background_color
    }

    override fun onDestroy() {
        setHasOptionsMenu(false)
        super.onDestroy()
    }

    protected fun setBackEnabled() {
        (activity as BaseActivity).setBackEnabled()
    }

    protected fun setBackDisabled() {
        (activity as BaseActivity).setBackDisabled()
    }

    fun add(containerId: Int, fragment: BaseFragment) {
        activity?.run {
            (this as BaseActivity).add(containerId, fragment)
        }
    }

    fun add(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean) {
        activity?.run {
            (this as BaseActivity).add(containerId, fragment, addToBackStack)
        }
    }

    fun replace(containerId: Int, fragment: BaseFragment) {
        activity?.run {
            (this as BaseActivity).replace(containerId, fragment)
        }
    }

    fun replace(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean) {
        activity?.run {
            (this as BaseActivity).replace(containerId, fragment, addToBackStack)
        }
    }

    fun pop() {
        activity?.run {
            if (isDialog) {
                dismiss()
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    }

    val stackCount: Int
        get() {
            return activity?.supportFragmentManager?.backStackEntryCount ?: 1
        }

    fun popAllFragmentsUpto(index: Int) {
        activity?.run {
            val fm = supportFragmentManager
            val count = fm.backStackEntryCount
            (0 until count - index).forEach { _ ->
                fm.popBackStack()
            }
        }
    }

    fun popFragments(count: Int) {
        activity?.run {
            val fm = supportFragmentManager
            (0 until count).forEach { _ ->
                fm.popBackStack()
            }
        }
    }

    open fun onHomeButtonPress() {
        //do nothing
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    open fun onBackPress() {
        if (isDialog) {
            dialog?.dismiss()
        } else {
            pop()
        }
    }


    protected fun unbindDrawables(view: View?) {
        view?.run {
            background?.run {
                callback = null
            }
            if (view is ViewGroup) {
                with(view) {
                    for (i in 0 until childCount) {
                        unbindDrawables(getChildAt(i))
                    }
                    removeAllViews()
                    setBackgroundResource(0)
                }
            }
        }
    }

    open fun onPermissionsGranted(requestCode: Int, permissions: List<String>) {
        //do nothing
    }

    open fun onPermissionsDenied(requestCode: Int, permissions: List<String>) {
        //do nothing
    }

    fun handlingEmptyView(view : TEmptyView, message : String?){
        with(view) {
            setEmptyResponse(null, SpannableString(message))
        }
    }

    fun handlingErrorView(view : TEmptyView, message : String?){
        with(view) {
            setEmptyOrErrorResponse(null,
                SpannableString(message), getString(R.string.lbl_alert_retry)) {
                this visibility gone
                retryFetch()
            }
        }
    }

    open fun retryFetch(){
         //do nothing
    }

}
