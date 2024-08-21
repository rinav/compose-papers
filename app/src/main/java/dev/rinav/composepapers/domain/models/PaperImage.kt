package dev.rinav.composepapers.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class PaperImage(
    val id: String,
    val imageUrlSmall: String,
    val imageUrlRegular: String,
    val imageUrlRaw: String,
    val blurHash: String?,
    val description: String?,
    val photographerName: String,
    val photographerUsername: String,
    val photographerProfileImage: String,
    val photographerProfileLink: String,
    val width: Int,
    val height: Int
): Parcelable
