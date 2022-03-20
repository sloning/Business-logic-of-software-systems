package com.bloss.controller

import com.bloss.dto.PostStatusChangeDto
import com.bloss.service.PostService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/moderator")
class ModeratorController(private val postService: PostService) {
    @PutMapping("/postStatus")
    fun changePostStatus(@Valid @RequestBody postStatusChangeDto: PostStatusChangeDto) =
        postService.changeStatus(postStatusChangeDto)

    @GetMapping("/post/{status}")
    fun getPostsByStatus(@PathVariable status: String, pageable: Pageable) =
        postService.findAllByStatus(status, pageable)
}