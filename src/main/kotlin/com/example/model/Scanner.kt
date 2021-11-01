package com.example.model
import com.example.view.MainView
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
    lateinit var report:String

    fun OpenProject() {
        report = ""

        directory = chooseDirectory()
        if(directory != null) {

            //check for version number here and perform appropriate logic

           //




            editorVersion = ExtractVersionNumber()
            settingsExtractor = ProjectSettingsExtractor(directory)
            settingsExtractor.ExtractSettings()
            PopulateSettingsValues()

            sceneExtractor = SceneExtractor(directory)
            scenesInBuild = sceneExtractor.ExtractScenesInBuild()
            totalScenesinAssetFolder = sceneExtractor.ExtractAllScenesFromAssets()

            packageList = PackageManifest(directory)
            packageList.LoadManifest()

            assets = AssetsExtractor(directory)
            assets.ScanAssetFolder()

            CreateReport()
        }
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

    fun CreateReport()
    {
        report = ""

        report += "Project Name: " + projectName + "\n"
        report += "Unity Version: " +editorVersion +"\n"


        report += "Directory: " + directory!!.path + "\n"

        report += "\nScene List:\n"

        for(scene in totalScenesinAssetFolder)
        {
            report += "     $scene\n"
        }

        report += "\nScenes In Build:\n"
        for(scene in scenesInBuild)
        {
            report += "     $scene\n"
        }

        report += "\nPackages:\n"
        for(packageName in packageList.packageList)
        {
            report += "     $packageName\n"
        }

        report += "\nAsset Breakdown:\n"
        report += "     ${assets.assetInfo}\n"

        report += "\nAsset List: \n"

        report += "\nImage List: \n"
        for(imageName in assets.imageList)
        {
           report += "     $imageName\n"

        }
        report += "\nText List: \n"
        for(text in assets.textList)
        {
            report += "     $text\n"
        }
        report += "\nAudio List: \n"
        for(sound in assets.audioList)
        {
            report += "     $sound\n"
        }
        report += "\nScript List: \n"
        for(script in assets.scriptList)
        {
            report += "     $script\n"
        }
        report += "\nNative File List: \n"
        for(native in assets.nativeList)
        {
            report += "     $native\n"
        }
        report += "\nModel List: \n"
        for(model in assets.modelList)
        {
            report += "     $model\n"
        }
        report += "\nPrefab List: \n"
        for(prefab in assets.prefabList)
        {
            report += "     $prefab\n"
        }
        report += "\nShader List: \n"
        for(shader in assets.shaderList)
        {
            report += "     $shader\n"
        }
        report += "\nVFX List: \n"
        for(vfx in assets.vfxList)
        {
            report += "     $vfx\n"
        }
        report += "\nFont List: \n"
        for(font in assets.fontList)
        {
            report += "     $font\n"
        }


    }


}


