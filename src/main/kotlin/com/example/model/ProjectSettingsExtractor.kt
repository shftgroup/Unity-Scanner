package com.example.model

import java.io.File
import java.nio.file.ReadOnlyFileSystemException

class ProjectSettingsExtractor(projectDirectory:File?) {

    //properties and constants
    val directory = projectDirectory
    val fileLines = ReadFileAsLinesUsingReadLines("") //this will need to change to search for file
    lateinit var projectName:String
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //will extract all settings by calling appropriate method
    fun ExtractSettings()
    {
        projectName = ExtractProjectName()
    }


    //methods that extract individual settings
    fun ExtractProjectName():String
    {
        for(line in fileLines)
        {
            val r = Regex("productName:")
            if(r.containsMatchIn(line))
            {
                // println(line)
                projectName = line.substringAfter(":")
                println(projectName)
            }
        }
        return projectName
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////




    // File IO operations for settings file
    // need to change this from hardcoded pathname
    fun ReadFileAsLinesUsingReadLines(fileName: String): List<String>
            = File(directory.toString() + "/ProjectSettings/ProjectSettings.ASSET").readLines()

    fun FileProjectSettingsFile(projectDirectory: File?):String
    {
        return ""

    }

}
