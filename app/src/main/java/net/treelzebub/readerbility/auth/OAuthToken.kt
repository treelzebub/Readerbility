package net.treelzebub.readerbility.auth

/**
 * Created by Tre Murillo on 1/1/16
 */
class OAuthToken {
    val token: String
    val secret: String

    constructor(token: String, secret: String) {
        this.token  = token
        this.secret = secret
    }
}
