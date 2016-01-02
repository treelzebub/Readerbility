package net.treelzebub.readerbility.auth

import retrofit.client.Request
import retrofit.client.Response
import retrofit.client.UrlConnectionClient
import javax.net.ssl.HttpsURLConnection

/**
 * Created by Tre Murillo on 1/1/16
 *
 * Uses gist: https://gist.github.com/akshaydashrath/10119943
 */
class ReadabilityClient : UrlConnectionClient() {

    override fun execute(request: Request): Response? {
        val connection = super.openConnection(request) as HttpsURLConnection
        try {
            prepareRequest(connection, request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun prepareRequest(connection: HttpsURLConnection, request: Request) {
        connection.setRequestProperty("Connection", "close")
        connection.requestMethod = request.method
        connection.doInput = true
        request.headers.forEach {
            connection.addRequestProperty(it.name, it.value)
        }
        val body = request.body
        var length = -1L
        if (body != null) {
            connection.doOutput = true
            length = body.length()
        }
//        RequestSigner.create()
    }
}
