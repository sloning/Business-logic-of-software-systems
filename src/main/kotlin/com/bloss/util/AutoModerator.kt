package com.bloss.util

import com.bloss.exception.NoBadWordsDictionaryException
import com.bloss.model.Post
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class AutoModerator {
    private val badWords =
        try {
            ClassPathResource("bad_words.txt").file.readLines().toSet()
        } catch (e: Exception) {
            throw NoBadWordsDictionaryException("No bad words dictionary found in class path")
            emptySet()
        }

    fun validate(post: Post): Boolean =
        listOf(post.name, post.description)
            .flatMap { it.split(" ") }
            .toSet()
            .intersect(badWords)
            .isNotEmpty()
}