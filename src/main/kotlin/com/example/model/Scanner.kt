package com.example.model
import tornadofx.*
import java.io.File

class Scanner {

    var directory:File? = null
    lateinit var editorVersion:String

    fun GetDirectory()
    {
        directory = chooseDirectory()
    }

    fun ExtractVersionNumber():String
    {
        val file = File(directory.toString() + "/ProjectSettings/ProjectVersion.txt")
        var content:String = file.readText()

        editorVersion = content.split("\n")[0].substringAfter(" ")

        println(editorVersion)
        println(editorVersion)

        return editorVersion
    }

    }


