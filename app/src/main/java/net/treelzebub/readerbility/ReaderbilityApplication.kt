package net.treelzebub.readerbility

import android.app.Application
import net.treelzebub.readerbility.util.BaseInjection

/**
 * Created by Tre Murillo on 1/1/16
 */
class ReaderbilityApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BaseInjection.context = this.applicationContext
    }
}
