package homelab.onlytake.advertise

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

fun initAdvertise(context: Context) {
    MobileAds.initialize(context) {}
}

fun activateView(adView: AdView) {
    adView.loadAd(AdRequest.Builder().build())
}
