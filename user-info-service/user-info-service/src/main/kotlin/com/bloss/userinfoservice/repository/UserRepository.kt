package com.bloss.userinfoservice.repository

import com.bloss.userinfoservice.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class UserRepository(private val jdbcTemplate: JdbcTemplate) {
    fun findById(id: String): User? {
        val sql = "select * from act_id_user where id_ = ?"

        return jdbcTemplate.query(sql, this::mapRowToUser, id)[0]
    }

    private fun mapRowToUser(rs: ResultSet, rowNum: Int): User {
        return User(
            id = rs.getString("id_"),
            email = rs.getString("email_"),
            password = rs.getString("pwd_"),
            firstName = rs.getString("first_"),
            secondName = rs.getString("last_"),
        )
    }
}