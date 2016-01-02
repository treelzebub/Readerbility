package net.treelzebub.readerbility.api.model

/**
 * Created by Tre Murillo on 1/1/16
 */
data class Tag(
        val id: Long?,
        val text: String?,
        val appliedCount: Int?,
        val bookmarkIds: Array<String>
)
