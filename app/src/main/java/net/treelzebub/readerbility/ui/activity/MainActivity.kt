package net.treelzebub.readerbility.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.treelzebub.readerbility.auth.LocalCredStore

/**
 * Created by Tre Murillo on 1/1/16
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (LocalCredStore.getToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
