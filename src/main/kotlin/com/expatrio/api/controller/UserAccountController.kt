package com.expatrio.api.controller

import com.expatrio.api.model.dto.UserAccountDto
import com.expatrio.api.model.response.ErrorResponse
import com.expatrio.api.model.response.SuccessResponse
import com.expatrio.api.service.UserAccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserAccountController(
    private val userAccountService: UserAccountService
) {
    @GetMapping
    fun findAllUsersAccounts(): ResponseEntity<List<UserAccountDto>> {
        val usersAccounts: List<UserAccountDto>  = userAccountService.findAllUsersAccounts()
        if(usersAccounts.isNotEmpty()) {
            return ResponseEntity(usersAccounts, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)

    }

    @GetMapping("/get-user-account/{userAccountId}")
    fun findUserAccountById (@PathVariable("userAccountId") userAccountId: Int): ResponseEntity<UserAccountDto> {
        val userAccountDto: UserAccountDto? = userAccountService.findUserAccountById(userAccountId)
        if(userAccountDto != null) {
            return ResponseEntity(userAccountDto, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/insert-user-account")
    fun insertNewUserAccount(@Valid @RequestBody userAccountDto: UserAccountDto): ResponseEntity<Any> {
        val rowCount: Int = this.userAccountService.insertNewUserAccount(userAccountDto, false)
        if(rowCount > 0) {
            return ResponseEntity(SuccessResponse("User Account Created Successfully"), HttpStatus.CREATED)
        }
        return ResponseEntity(ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "User Account Not Created" ,arrayListOf()), HttpStatus.BAD_REQUEST)
    }

    @PutMapping("/update-user-account/{userAccountId}")
    fun updateUserAccount(@PathVariable("userAccountId") userAccountId: Int,@Valid @RequestBody userAccountDto: UserAccountDto): ResponseEntity<Any> {
        val rowCount: Int = this.userAccountService.updateUserAccount(userAccountId, userAccountDto)
        if(rowCount > 0) {
            return ResponseEntity(SuccessResponse("User Account Updated Successfully"), HttpStatus.OK)
        }
        return ResponseEntity(ErrorResponse(HttpStatus.NOT_FOUND.toString(),"User Account Not Found", arrayListOf()), HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/delete-user-account/{userAccountId}")
    fun deleteUserAccount(@PathVariable("userAccountId") userAccountId: Int): ResponseEntity<Any> {
        val rowCount: Int = this.userAccountService.deleteUserAccount(userAccountId)
        if(rowCount > 0) {
            return ResponseEntity(SuccessResponse("User Account Deleted Successfully"), HttpStatus.OK)
        }
        return ResponseEntity(ErrorResponse(HttpStatus.NOT_FOUND.toString(),"User Account Not Found", arrayListOf()), HttpStatus.NOT_FOUND)
    }
}