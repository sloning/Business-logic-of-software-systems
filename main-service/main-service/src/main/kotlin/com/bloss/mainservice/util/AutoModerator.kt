package com.bloss.mainservice.util

import com.bloss.mainservice.exception.NoBadWordsDictionaryException
import com.bloss.mainservice.model.Post
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
        }

    fun validate(post: Post): Boolean =
        listOf(post.name, post.description)
            .flatMap { it.split(" ") }
            .toSet()
            .intersect(badWords)
            .isEmpty()
}