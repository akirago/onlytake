package homelab.onlytake

import android.app.Application
import android.content.Context

class CustomApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        context = applicationContext
        super.onCreate()
    }
}
