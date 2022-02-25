package com.bloss.util

import com.bloss.exception.NoBadWordsDictionaryException
import com.bloss.model.Post
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

@Component
class AutoModerator {
    private val badWords =
        try {
            BufferedReader(InputStreamReader(ClassPathResource("bad_words.txt").inputStream))
                .lines().collect(Collectors.toSet())
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