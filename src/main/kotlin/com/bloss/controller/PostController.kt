package com.bloss.controller

import com.bloss.model.Post
import com.bloss.service.PostService
import com.bloss.util.CreatorChecker
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService,
    private val creatorChecker: CreatorChecker
) {
    @GetMapping
    fun getAll(pageable: Pageable): Page<Post> = postService.findAll(pageable)

    @PostMapping
    fun create(@Valid @RequestBody post: Post) = postService.create(post)

    @PutMapping
    @PreAuthorize("@creatorChecker.check(#post)")
    fun update(@Valid @RequestBody post: Post) = postService.update(post)

    @PutMapping
    @PreAuthorize("@creatorChecker.check(#post)")
    fun upgradePost(@Valid @RequestBody post: Post) = postService.upgradePost(post)

    @DeleteMapping
    @PreAuthorize("@creatorChecker.check(#post)")
    fun delete(@Valid @RequestBody post: Post) = postService.delete(post)
}
