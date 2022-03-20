package com.bloss.controller

import com.bloss.dto.RoleChangeDto
import com.bloss.dto.UserStatusChangeDto
import com.bloss.service.UserService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/admin")
class AdministratorController(private val userService: UserService) {
    @PutMapping("/role")
    fun changeRole(@Valid @RequestBody roleChangeDto: RoleChangeDto) = userService.changeRole(roleChangeDto)

    @PutMapping("/userStatus")
    fun changeUserStatus(@Valid @RequestBody userStatusChangeDto: UserStatusChangeDto) =
        userService.changeStatus(userStatusChangeDto)
}