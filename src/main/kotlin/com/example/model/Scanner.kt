package com.example.model
import tornadofx.*
import java.io.File
import java.util.*


class Scanner {

    ///Properties and constants
    var directory: File? = null

    lateinit var settingsExtractor: ProjectSettingsExtractor
    lateinit var sceneExtractor:SceneExtractor
    lateinit var packageList:PackageManifest
    lateinit var assets:AssetsExtractor


    lateinit var editorVersion: String
    lateinit var projectName: String
    lateinit var scenesInBuild: List<String>
    lateinit var totalScenesinAssetFolder: List<String>

    lateinit var assetInfo:String
    ////////////////////////////////////////////////////////////////////////////////////////////////////


    fun OpenProject() {
        directory = chooseDirectory()
        editorVersion = ExtractVersionNumber()
        settingsExtractor = ProjectSettingsExtractor(directory)
        settingsExtractor.ExtractSettings()
        PopulateSettingsValues()

        sceneExtractor = SceneExtractor(directory)
        scenesInBuild =  sceneExtractor.ExtractScenesInBuild()
        totalScenesinAssetFolder = sceneExtractor.ExtractAllScenesFromAssets()

        packageList = PackageManifest(directory)
        packageList.LoadManifest()

        assets = AssetsExtractor(directory)
        assets.ScanAssetFolder()
      //  assetInfo = assets.as
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


