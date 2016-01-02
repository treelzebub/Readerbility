package net.treelzebub.readerbility.util

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Created by Tre Murillo on 1/1/16
 * Copyright(c) 2016 Level, Inc.
 */
class AuthUtils {

    interface NonceGenerator {
        fun create(): String
    }

    interface TimestampGenerator {
        fun create(): Long
    }

    object Encoder {
        fun encode(s: String): String {
            try {
                return URLEncoder.encode(s, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                return s
            }
        }
    }
}
