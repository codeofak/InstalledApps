package com.acode.installedapps.viewModels

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel


class ViewModelApps : ViewModel() {


    fun listAppsC(context: Context): List<String> {
        val pm = context.packageManager
        val appInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        var string = emptyList<String>()

        appInfoList.forEach {
            if (pm.getLaunchIntentForPackage(it.packageName) != null) {
                string = string.plus(it.loadLabel(pm).toString())
            }
        }
        return string
    }
}