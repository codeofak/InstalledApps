package com.acode.installedapps.viewModels

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel


class ViewModelApps(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    init {
        loadAppPackages(context = context)
    }

    fun loadAppPackages(context: Context): MutableList<ApplicationInfo> {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val appPackages: MutableList<ApplicationInfo> =
            kotlin.collections.emptyList<ApplicationInfo>()
                .toMutableList()

        packages.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                appPackages += it
            }
        }

        return appPackages
    }

}














