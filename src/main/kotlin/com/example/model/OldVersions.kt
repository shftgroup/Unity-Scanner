package com.example.model

import java.io.File

object OldVersions {



    var editorVersion = 0

    var scenesInBuild = mutableListOf<String>()

    fun FindEditorVersion(buildSettingsFile: File):String
    {
        val bytes = buildSettingsFile.readBytes()

        var text = String()

        for(i in 0..bytes.count()-1)
        {
            val c = bytes[i].toChar()
           // if(c in '0'..'9' || c in 'a'..'z')
            if(c == '.' && bytes[i-1].toChar() in '0'..'9' )
            {
               // print(bytes[i].toChar())
                text += bytes[i-1].toChar()
                text += c
                text += bytes[i+1].toChar()
                text += bytes[i+2].toChar()
                text += bytes[i+3].toChar()
                break;
            }

        }


        return text
    }

    fun ExtractScenesInBuild(buildSettingsFile: File):List<String>
    {
        if(buildSettingsFile.exists()) {
            val bytes = buildSettingsFile.readBytes()

            var text = String()

            for (i in 0..bytes.count() - 1) {
                val c = bytes[i].toChar()
                text += c
            }

            val pattern = "Assets/(.+)unity".toRegex()

            val scenes = pattern.findAll(text).map { it.value }.toList()

            if (scenes.count() != 0) {
                var sceneList = mutableListOf<String>()

                sceneList = scenes[0].split(".unity").toMutableList()
                println(sceneList)
                // if(sceneList.count() > 1)
                sceneList.removeAt(sceneList.count() - 1)  //this trims the last element which is empty
                return sceneList
            } else {
                return listOf()
            }
        }
        else
        {
            return listOf("Can't Find Scenes")
        }
    }

    fun ExtractProjectName(projectSettingsFile: File):String
    {
        if(projectSettingsFile.exists()) {
            val bytes = projectSettingsFile.readBytes()

            var name = ""

            for (byte in 68..bytes.count() - 1) //68 is the byte offset where the project name should begin
            {
                val c = bytes[byte].toChar()

                if (c.toInt() == 0)
                    break;

                name += c
            }

            // println("Name: " + name)
            return name
        }
        else
        {
            return "Can't Find Project Name"
        }
    }
}