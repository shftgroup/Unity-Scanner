package com.example.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import java.io.File

class SceneExtractor(projectDirectory: File?, version:Int) {

    //properties and constants
    val directory = projectDirectory
    val fileLines = ReadFileAsLinesUsingReadLines("") //this will need to change to search for file

    val editorVersion = version

    var scenesInBuild = 0
    var scenesInAssetFolder = 0

    var oldVersion = false

    val oldVersionFileName = directory.toString() + "/Library/EditorBuildSettings.asset"
    val unityVersion45FileName = directory.toString() + "/ProjectSettings/EditorBuildSettings.asset"

    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    fun ExtractScenesInBuild():List<String>
    {
        var returnList = listOf<String>()

        if(oldVersion == false)
        {
            for (line in fileLines) {
                val r = Regex("path")

                var sceneName = String()

                if (r.containsMatchIn(line)) {


                    // println(line)
                    //sceneName = line.substringAfter("Levels/").substringBefore(".")

                    sceneName = line.substringAfterLast("/").substringBefore(".")

                    if (sceneName.contains(Regex("path"))) {
                        continue
                    } else {
                        returnList += sceneName
                        scenesInBuild += 1
                    }
                }
            }
        }
        else
        {
            if(editorVersion <= 3) {
                val editorBuildScenesFile = File(oldVersionFileName)
                OldVersions.editorVersion = editorVersion
                returnList = OldVersions.ExtractScenesInBuild(editorBuildScenesFile)
            }
            else
            {
                val editorBuildScenesFile = File(unityVersion45FileName)
                OldVersions.editorVersion = editorVersion
                returnList = OldVersions.ExtractScenesInBuild(editorBuildScenesFile)
            }
        }
        //println("Total Scenes in build:" + scenesInBuild)
        //println(returnList)




        return returnList
    }

    fun ExtractAllScenesFromAssets():List<String>
    {
        var returnList = listOf<String>()

        File(directory.toString() +"/Assets").walk().forEach {
            if(it.extension == "unity")
            {
               // println(it.name)
                scenesInAssetFolder += 1
                returnList += it.name
            }
        }

       // println("Total Scenes in assets folder: " + scenesInAssetFolder.toString())
      //  println(returnList)

        return returnList


    }



    // File IO operations for build settings file
    // need to change this from hardcoded pathname
    fun ReadFileAsLinesUsingReadLines(fileName: String): List<String>
    {
       // if( File(directory.toString() + "/ProjectSettings/EditorBuildSettings.ASSET").readLines()
        if(editorVersion <= 5)
        {
            oldVersion = true
            return listOf()
        }

        val projectSettingsFilename = directory.toString() + "/ProjectSettings/EditorBuildSettings.ASSET"

        if(File(projectSettingsFilename).exists())
        {
            return File(projectSettingsFilename).readLines()
            //print("Found project settings file")
        }
        else
        {
            oldVersion = true
            return listOf()
        }

    }
}
