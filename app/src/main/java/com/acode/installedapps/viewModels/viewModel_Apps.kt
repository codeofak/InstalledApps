package com.acode.installedapps.viewModels

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import java.util.Collections.emptyList
import android.content.Context as Context1


class ViewModelApps(application: Application) : AndroidViewModel(application) {



    fun packageDetail(context: Context1): MutableList<ApplicationInfo> {
        val pm = context.packageManager
        val appInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        return appInfoList
    }


    fun applicationLabel(context: Context1) {


        val pm = context.packageManager
        val packages = packageDetail(context)

        var appLabel = emptyList<String>()
        var appIcon = emptyList<Int>()

        packages.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                appLabel = appLabel.plus(it.loadLabel(pm).toString())
                appIcon = appIcon.plus(it.icon)
            }
        }
//        return appLabel
//        return appIcon
    }


    fun applicationIcon(context: Context1) {
        val pm = context.packageManager
        val packages = packageDetail(context = context)

        var appIcon = ""

        packages.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                appIcon = appIcon.plus(it.icon)
            }
        }
    }

    fun applicationPackages(context: Context1): List<ApplicationInfo> {
        val pm = context.packageManager
        val appInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        val appPackages = listOf<ApplicationInfo>()


        appInfoList.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                appPackages.plus(it)
            }
        }
        return appPackages

    }
}














