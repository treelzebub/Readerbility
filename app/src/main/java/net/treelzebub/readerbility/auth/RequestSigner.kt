package net.treelzebub.readerbility.auth

import net.treelzebub.readerbility.util.AuthUtils
import okio.Buffer
import okio.ByteString
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by Tre Murillo on 1/1/16
 *
 * Sign an OkHttp Request with an OAuth 1.0a signature
 */
class RequestSigner {
    private val SIGNATURE_TYPE = "HmacSHA1"

    private final val consumerKey: String?
    private final val tokenValue: String?
    private final val nonce: AuthUtils.NonceGenerator?
    private final val timestamp: AuthUtils.TimestampGenerator?

    constructor(b: Builder) {
        consumerKey = b.consumerKey
        tokenValue  = b.tokenValue
        nonce       = b.nonce
        timestamp   = b.timestamp
    }

    fun initKey(consumerSecret: String, tokenSecret: String?) {
        val key = Buffer()
            .writeUtf8(AuthUtils.Encoder.encode(consumerSecret))
            .writeUtf8("&")
        if (tokenSecret != null && tokenSecret.length > 0) {
            key.writeUtf8(AuthUtils.Encoder.encode(tokenSecret))
        }
    }

    class Builder() {
        var consumerKey: String                      by Delegates.notNull()
        var consumerSecret: String                   by Delegates.notNull()
        var tokenValue: String                       by Delegates.notNull()
        var tokenSecret: String                      by Delegates.notNull()
        var nonce: AuthUtils.NonceGenerator?         = null
        var timestamp: AuthUtils.TimestampGenerator? = null

        fun consumer(key: String, secret: String): Builder {
            consumerKey    = key
            consumerSecret = secret
            return this
        }

        fun token(value: String): Builder {
            tokenValue = value
            return this
        }

        fun token(value: String, secret: String): Builder {
            tokenValue  = value
            tokenSecret = secret
            return this
        }

        fun nonce(nonce: AuthUtils.NonceGenerator): Builder {
            this.nonce = nonce
            return this
        }

        fun timestamp(timestamp: AuthUtils.TimestampGenerator): Builder {
            this.timestamp = timestamp
            return this
        }

        fun build(): RequestSigner {
            if (nonce == null) {
                nonce = DefaultNonceGenerator()
            }
            if (timestamp == null) {
                timestamp = DefaultTimestampGenerator()
            }
            val rs = RequestSigner(this)
            try {
                rs.initKey(consumerSecret, tokenSecret)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            }
            consumerSecret = ""
            tokenSecret    = ""
            return rs
        }
    }

    class DefaultNonceGenerator : AuthUtils.NonceGenerator {
        override fun create(): String {
            val b = ByteArray(32)
            Random().nextBytes(b)
            return ByteString.of(*b).hex()
        }
    }

    class DefaultTimestampGenerator() : AuthUtils.TimestampGenerator {
        override fun create(): Long {
            return System.currentTimeMillis() / 1000L
        }
    }
}
