package com.isgarsi.spanbbowl.models

import java.util.*

data class Credentials(
//    val userId: String = "",
    var name: String = "",
    var username: String = "",
    var password: String = "",
    val createdAt: Date = Date(),
    var imageURL: String = "",
    val credentialsId: String = "",
    var showPassword: Boolean = false
)