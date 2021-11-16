package com.example.model

import java.io.File
import java.nio.file.ReadOnlyFileSystemException

class ProjectSettingsExtractor(projectDirectory:File?) {

    //properties and constants
    val directory = projectDirectory
    val fileLines = ReadFileAsLinesUsingReadLines("") //this will need to change to search for file
    lateinit var projectName:String

    var oldVersion = false

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //will extract all settings by calling appropriate method
    fun ExtractSettings()
    {
        projectName = ExtractProjectName()
    }


    //methods that extract individual settings
    fun ExtractProjectName():String
    {
        projectName = "Unknown - Early Unity Version"


        //here we need to deal with if the name is not showing up properly

        if(oldVersion == false)
        {
            for (line in fileLines) {
                val r = Regex("productName:")
                if (r.containsMatchIn(line)) {
                    // println(line)
                    projectName = line.substringAfter(":")
                    //  println(projectName)
                }
            }
        }
        else
        {
            val projectSettingsFileName = File(directory.toString() + "/Library/ProjectSettings.asset")
            projectName = OldVersions.ExtractProjectName(projectSettingsFileName)
        }
        return projectName
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////




    // File IO operations for settings file
    // need to change this from hardcoded pathname
    fun ReadFileAsLinesUsingReadLines(fileName: String): List<String>
    {
        val projectSettingsFileName = directory.toString() + "/ProjectSettings/ProjectSettings.ASSET"

        if(File(projectSettingsFileName).exists()) {
            return File(directory.toString() + "/ProjectSettings/ProjectSettings.ASSET").readLines()
        }
        else
        {
           // print("project settings not found")
            oldVersion = true
            return listOf("Empty")
        }
        //EditorBuildSettings.asset
    }
    fun FileProjectSettingsFile(projectDirectory: File?):String
    {
        return ""

    }

}
