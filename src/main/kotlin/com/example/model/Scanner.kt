package com.example.model
import com.example.view.MainView
import tornadofx.*
import java.io.File
import java.util.*


class Scanner {

    ///Properties and constants
    var directory: File? = null

    var settingsExtractor = ProjectSettingsExtractor(File(""),0)
    var sceneExtractor = SceneExtractor(File(""),0)
    var packageList = PackageManifest(File(""))
    var assets = AssetsExtractor(File(""))


    var editorVersion = "0"
    var projectName =  String()
    var scenesInBuild = listOf<String>()
    var totalScenesinAssetFolder = listOf<String>()

    var assetInfo = String()
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    var report = String()

    var packageManagerAvailable = true

    fun OpenProject() {
        report = ""




        directory = chooseDirectory()
        if(directory != null) {

            //check for version number here and perform appropriate logic

           //
            //need to check if we are in a valid directory

            val assetFolderName = directory.toString() + "/assets"

            if(File(assetFolderName).exists()) {

                editorVersion = ExtractVersionNumber()

                val version = editorVersion.substringBefore('.').toInt()
                //println("Version: " + version)
                //get project name
                //this needs to be refactored to set the name directly
                settingsExtractor = ProjectSettingsExtractor(directory,version)
                settingsExtractor.ExtractSettings()
                PopulateSettingsValues()
               // println("Version: " + version)
                sceneExtractor = SceneExtractor(directory,version)
                scenesInBuild = sceneExtractor.ExtractScenesInBuild()
                totalScenesinAssetFolder = sceneExtractor.ExtractAllScenesFromAssets()

                if(version <= 2017)
                {
                    packageManagerAvailable = false;
                }
                else {
                    packageList = PackageManifest(directory)
                    packageList.LoadManifest()
                }
                assets = AssetsExtractor(directory)
                assets.ScanAssetFolder()

                CreateReport()
            }
            else
            {
                print("Cannot Locate Assets, This appears to not be a unity root directory")
                projectName = "Cannot Locate Assets, This appears to not be a unity root directory"
            }
        }
      //  assetInfo = assets.as
    }

    //This will find the version number.  Will need to be modified to search for file
    //and remove hardcoded path
    fun ExtractVersionNumber(): String {
        //look here first for modern unity, then go to Library/proje
        val file = File(directory.toString() + "/ProjectSettings/ProjectVersion.txt")

        //start by assuming a modern unity version so check for projectVersion.txt
       if(file.exists()) {
           var content: String = file.readText()

           editorVersion = content.split("\n")[0].substringAfter(" ")

         //  println("Editor Version is: " + editorVersion)

           return editorVersion
       }
        else //most likely an older version - check Library/EditorBuildSettings.asset
       {
            val editorBuildSettingsFile = File(directory.toString() + "/Library/EditorUserBuildSettings.asset")
           if(editorBuildSettingsFile.exists()) {
               editorVersion = OldVersions.FindEditorVersion(editorBuildSettingsFile)
           }
           else
           {
               return "0"
           }


            return editorVersion
       }
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

    fun ClearData()
    {


        settingsExtractor = ProjectSettingsExtractor(File(""),0)
        sceneExtractor = SceneExtractor(File(""),0)
        packageList = PackageManifest(File(""))
        assets = AssetsExtractor(File(""))


        editorVersion = "0"
        projectName =  String()
        scenesInBuild = listOf<String>()
        totalScenesinAssetFolder = listOf<String>()

        assetInfo = String()
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        report = String()

        packageManagerAvailable = true

    }

}


