package com.example.model
import tornadofx.*
import java.io.File
import java.util.*


class Scanner {

    ///Properties and constants
    var directory: File? = null

    lateinit var settingsExtractor: ProjectSettingsExtractor
    lateinit var sceneExtractor:SceneExtractor

   //should create a struct to hold these values
    lateinit var editorVersion: String
    lateinit var projectName: String
    lateinit var scenesInBuild: List<String>

    ////////////////////////////////////////////////////////////////////////////////////////////////////


    fun OpenProject() {
        directory = chooseDirectory()
        editorVersion = ExtractVersionNumber()
        settingsExtractor = ProjectSettingsExtractor(directory)
        settingsExtractor.ExtractSettings()
        PopulateSettingsValues()

        sceneExtractor = SceneExtractor(directory)
        scenesInBuild =  sceneExtractor.ExtractScenesInBuild()

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

        projectName = settingsExtractor.projectName

    }


}


