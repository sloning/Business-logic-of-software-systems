package com.bloss.repository

import com.bloss.model.ROLE
import com.bloss.model.User
import org.springframework.beans.factory.annotation.Value
import java.beans.XMLDecoder
import java.beans.XMLEncoder
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object UserXmlRepository {
    @Value("\${app.xmlUserRepository}")
    private val repoPath: String = "./user_repository"
    private var lastId: Long = 0

    init {
        File(repoPath).mkdirs()
        lastId = findAll().maxOfOrNull(User::id)?.toLong() ?: 0
    }

    private fun fileFactory(user: User) = File("$repoPath/${user.email}.xml")

    fun save(user: User): User {
        val write: (User) -> Unit = {
            XMLEncoder(FileOutputStream(fileFactory(user).apply { createNewFile() })).use { it.writeObject(user) }
        }

        val file = fileFactory(user)
        if (file.exists()) {
            file.delete()
            write(user)
        } else write(user.apply {
            lastId++
            id = lastId
        })

        return user
    }

    fun findByEmail(email: String): User? =
        try {
            val userFile = File(repoPath).listFiles { _, name -> name.contains(email) }.first()
            XMLDecoder(FileInputStream(userFile)).use { it.readObject() as User }
        } catch (e: Exception) {
            null
        }

    fun existsByEmail(email: String): Boolean = findByEmail(email) != null

    fun findRoleByEmail(email: String): ROLE? = findByEmail(email)?.role

    fun findById(id: Long): User? = findAll().find { it.id == id }

    fun existsById(id: Long): Boolean = findById(id) != null

    private fun findAll(): List<User> =
        File(repoPath).listFiles()?.mapNotNull { file ->
            try {
                XMLDecoder(FileInputStream(file)).use { it.readObject() as User }
            } catch (e: Exception) {
                null
            }
        }?.toList() ?: emptyList()

    fun delete(user: User) = fileFactory(user).delete()
}