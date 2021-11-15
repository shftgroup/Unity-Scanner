package com.example.model

import java.io.File

object OldVersions {

    var editorVersion = ""

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
                print(bytes[i].toChar())
                text += bytes[i-1].toChar()
                text += c
                text += bytes[i+1].toChar()
                text += bytes[i+2].toChar()
                text += bytes[i+3].toChar()
                break;
            }

        }
        //46-122 Ascii
       // println(text)

        //println(text)

      //  val pattern = "[0..9].[0..9].[0..9]".toRegex()

       // val version = pattern.find(text)
         //   println("\n" + version)

        return text
    }


}