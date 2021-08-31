package com.example.model
import tornadofx.*
import java.io.File



class Scanner {

    ///Properties and constants
    var directory: File? = null

    lateinit var SettingsExtractor: ProjectSettingsExtractor


   //should create a struct to hold these values
    lateinit var editorVersion: String
    lateinit var projectName: String

    ////////////////////////////////////////////////////////////////////////////////////////////////////


    fun OpenProject() {
        directory = chooseDirectory()
        editorVersion = ExtractVersionNumber()
        SettingsExtractor = ProjectSettingsExtractor(directory)
        SettingsExtractor.ExtractSettings()
        PopulateSettingsValues()

    }

    //This will find the version number.  Will need to be modified to search for file
    //and remove hardcoded path
    fun ExtractVersionNumber(): String {
        val file = File(directory.toString() + "/ProjectSettings/ProjectVersion.txt")
        var content: String = file.readText()

        editorVersion = content.split("\n")[0].substringAfter(" ")

        return editorVersion
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    fun PopulateSettingsValues() {

        projectName = SettingsExtractor.projectName

    }
}


