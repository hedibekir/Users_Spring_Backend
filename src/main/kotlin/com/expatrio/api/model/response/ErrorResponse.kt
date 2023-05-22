package com.expatrio.api.model.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorResponse(
    val errorCode: String,
    val errorMessage: String?,
    val errorsList: MutableList<String?>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErrorResponse

        if (errorCode != other.errorCode) return false
        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = errorCode.hashCode()
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        return result
    }
}
