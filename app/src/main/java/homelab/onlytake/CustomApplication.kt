package homelab.onlytake

import android.app.Application

class CustomApplication : Application() {

    companion object {
        val instance = CustomApplication()
    }
}
