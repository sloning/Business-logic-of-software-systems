package com.bloss.controller

import com.bloss.dto.PostStatusChangeDto
import com.bloss.service.PostService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/moderator")
class ModeratorController(private val postService: PostService) {
    @PutMapping("/postStatus")
    fun changePostStatus(@Valid @RequestBody postStatusChangeDto: PostStatusChangeDto) =
        postService.changeStatus(postStatusChangeDto)
}