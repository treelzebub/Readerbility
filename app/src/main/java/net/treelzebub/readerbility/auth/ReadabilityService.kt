package net.treelzebub.readerbility.auth

import net.treelzebub.readerbility.api.model.Article
import net.treelzebub.readerbility.api.model.User
import retrofit.http.GET
import retrofit.http.Path

/**
 * Created by Tre Murillo on 1/1/16
 */
interface ReadabilityService {

    @GET("/users/_current")
    fun identity(): User

    @GET("/articles/{article_id}")
    fun articles(@Path("article_id") id: String): List<Article>

    // ...
}
