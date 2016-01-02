package net.treelzebub.readerbility.util

import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by Tre Murillo on 1/1/16
 */
object BaseInjection {

    var context: Context by Delegates.notNull()
}
