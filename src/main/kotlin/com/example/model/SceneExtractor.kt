package com.example.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import java.io.File

class SceneExtractor(projectDirectory: File?) {

    //properties and constants
    val directory = projectDirectory
    val fileLines = ReadFileAsLinesUsingReadLines("") //this will need to change to search for file

    var scenesInBuild = 0
    var scenesInAssetFolder = 0




    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    fun ExtractScenesInBuild():List<String>
    {
        var returnList = listOf<String>()


        for(line in fileLines)
        {
            val r = Regex("path")

            var sceneName = String()

            if(r.containsMatchIn(line))
            {


                // println(line)
                //sceneName = line.substringAfter("Levels/").substringBefore(".")

                sceneName = line.substringAfterLast("/").substringBefore(".")

                if(sceneName.contains(Regex("path"))){
                    continue
                }
                else
                {
                    returnList += sceneName
                    scenesInBuild += 1
                }
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


        val projectSettingsFilename = directory.toString() + "/ProjectSettings/EditorBuildSettings.ASSET"

        if(File(projectSettingsFilename).exists())
        {
            return File(projectSettingsFilename).readLines()
            print("Found project settings file")
        }
        else
        {
           // print("project settings not found")
            return listOf("Empty")
        }

    }
}