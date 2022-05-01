package com.bloss.userinfoservice.service

import com.bloss.userinfoservice.model.Archive
import com.bloss.userinfoservice.model.Post
import com.bloss.userinfoservice.model.User
import com.bloss.userinfoservice.util.EmailUtil
import com.google.gson.Gson
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter

@Service
class ArchiveService(
    private val userService: UserService,
    private val postService: PostService,
    private val gson: Gson,
    private val emailUtil: EmailUtil
) {
    private val directoryName = "user_data"
    private val subject = "Your data"
    private val text = "Here is the file which contains all the information we know about you"

    fun makeArchive(userId: Long) {
        val user: User = userService.findById(userId)
        val content = getContent(user)

        val filePath = createFile(userId)
        writeToFile(content, filePath)

        emailUtil.sendMessageWithAttachment(user.email, subject, text, filePath)
    }

    private fun getContent(user: User): String {
        val posts: List<Post> = postService.findAllByUserId(user.id)
        val archive = Archive(user, posts)
        return gson.toJson(archive)
    }

    private fun createFile(userId: Long): String {
        createDirectory()

        val jsonFile = File("$directoryName/$userId.json")
        if (!jsonFile.createNewFile()) {
            jsonFile.delete()
            jsonFile.createNewFile()
        }
        return jsonFile.absolutePath
    }

    private fun createDirectory() {
        val file = File(directoryName)
        if (!file.exists()) {
            file.mkdir()
        }
    }

    private fun writeToFile(content: String, path: String) {
        val fileWriter = FileWriter(path)
        fileWriter.write(content)
        fileWriter.close()
    }
}