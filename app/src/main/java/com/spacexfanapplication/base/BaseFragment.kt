package com.spacexfanapplication.base

import android.os.Bundle
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.spacexfanapplication.utils.observeOnce

abstract class BaseFragment : Fragment(){

    /**
     * Use this method to observe any livedata
     */
    fun <T> addObserver(liveData: LiveData<T>?, observer: Observer<T>) {
        liveData?.observe(this, observer)
    }

    /**
     * Use this method to observe any livedata
     */
    fun <T> addSingleObserver(liveData: LiveData<T>?, observer: Observer<T>) {
        liveData?.removeObservers(this)
        liveData?.observe(this, observer)
    }


    /**
     * Use this method to remove observe any livedata
     */
    fun <T> removeObserver(liveData: LiveData<T>?, observer: Observer<T>) {
        liveData?.removeObserver(observer)
    }

    /**
     * Use this method to observe any livedata
     */
    fun <T> addObserverOnce(liveData: LiveData<T>?, observer: Observer<T>) {
        liveData?.observeOnce(this, observer)
    }

    open fun popBackStack(): Boolean {
        return false
    }

    companion object {

        @JvmStatic
        fun replaceFragment(
                activity: FragmentActivity,
                fragmentContainer: Int,
                fragment: BaseFragment,
                tag: String = "",
                addToBackStack: Boolean = true) {


            val transaction = activity.supportFragmentManager.beginTransaction()

            if (addToBackStack) {
                transaction
                        .replace(fragmentContainer, fragment)
                        .addToBackStack(fragment.javaClass.simpleName)
                        .commit()
            } else {
                transaction
                        .replace(fragmentContainer, fragment)
                        .commit()
            }

        }


        @JvmStatic
        fun replaceChildFragment(
                childFragmentManager: FragmentManager,
                fragmentContainer: Int,
                fragment: BaseFragment,
                arg: Bundle? = null
        ) {
            if (arg != null && fragment.arguments != null) {
                throw IllegalStateException("Your one argument gonna be lost")
            }
            fragment.arguments = arg
            childFragmentManager
                    .beginTransaction()
                    .replace(fragmentContainer, fragment)
                    .commit()

        }

        @JvmStatic
        fun replaceFragmentAllowingStateLoss(
                activity: FragmentActivity,
                fragmentContainerId: Int,
                fragment: BaseFragment,
                tag: String = "", addToBackStack: Boolean = true) {

            val transaction = activity.supportFragmentManager.beginTransaction()

            if (addToBackStack) {
                transaction
                        .replace(fragmentContainerId, fragment)
                        .addToBackStack(fragment.javaClass.simpleName)
                        .commitAllowingStateLoss()
            } else {
                transaction
                        .replace(fragmentContainerId, fragment)
                        .commitAllowingStateLoss()
            }


        }

        @JvmStatic
        fun showFragmentAsDialog(activity: FragmentActivity, fragment: DialogFragment) {
            val ft = activity.supportFragmentManager.beginTransaction()
            val previousDialog = activity.supportFragmentManager.findFragmentByTag("dialog")

            if (previousDialog != null) {
                ft.remove(previousDialog)
            }

            fragment.show(activity.supportFragmentManager, "dialog")
        }

        @JvmStatic
        fun showFragmentAsDialog(fragmentManger: FragmentManager, fragment: DialogFragment) {
            fragment.show(fragmentManger, "dialog")
        }

        @JvmStatic
        fun showFragmentAsDialogAllowingStateLoss(activity: FragmentActivity, fragment: BaseFragment) {

            activity.supportFragmentManager
                    .beginTransaction()
                    .show(fragment)
                    .commitAllowingStateLoss()
        }


        @JvmStatic
        fun addFragment(activity: FragmentActivity, fragmentContainerId: Int, fragment: BaseFragment, addToBackStack: Boolean = false) {

            //old code
            /*activity.supportFragmentManager
                    .beginTransaction()
                    .add(fragmentContainerId, fragment)
                    .commit()*/


            val transaction = activity.supportFragmentManager.beginTransaction()
            if (addToBackStack) {
                transaction
                        .add(fragmentContainerId, fragment)
                        .addToBackStack(fragment.javaClass.simpleName)
                        .commit()
            } else {
                transaction
                        .add(fragmentContainerId, fragment)
                        .commit()
            }
        }

        @JvmStatic
        fun popFragment(activity: FragmentActivity?) {
            activity ?: return
            if (activity.supportFragmentManager.backStackEntryCount > 1) {
                activity.supportFragmentManager.popBackStack()
            } else {
                activity.finish()
            }
        }

        @JvmStatic
        fun popFragment(manager: FragmentManager) {
            manager.popBackStack()
        }

        @JvmStatic
        fun popBackStack(manager: FragmentManager?) {
            if (manager != null) {
                try {
                    manager.popBackStack()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        @JvmStatic
        fun popBackStackImmediate(manager: FragmentManager?) {
            if (manager != null) {
                try {
                    manager.popBackStackImmediate()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        @JvmStatic
        fun popToStart(activity: FragmentActivity) {
            val fragmentManager = activity.supportFragmentManager
            if (fragmentManager.backStackEntryCount > 1) {
                fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(1).id, FragmentManager.POP_BACK_STACK_INCLUSIVE)


            }
        }

        @JvmStatic
        fun removeFragmentByTag(manager: FragmentManager, tag: String) {
            val fragment = manager.findFragmentByTag(tag)
            if (fragment != null) {
                try {
                    manager.beginTransaction().remove(fragment).commit()
                } catch (ex: IllegalStateException) {
                    manager.beginTransaction().remove(fragment)
                            .commitAllowingStateLoss()
                }
            }
        }
    }
}