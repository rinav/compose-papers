package dev.rinav.composepapers.data.mapper

import dev.rinav.composepapers.data.remote.dto.PapersDto
import dev.rinav.composepapers.domain.models.PaperImage

fun PapersDto.toPaperImage() : PaperImage {
    return PaperImage(
        id = this.id,
        description = description,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        blurHash = this.blurHash,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImage = this.user.profileImage.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height
    )
}

fun List<PapersDto>.toPaperImageList() : List<PaperImage> {
    return this.map { it.toPaperImage() }
}