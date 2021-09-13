package com.example.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import java.io.File

class SceneExtractor(projectDirectory: File?) {

    //properties and constants
    val directory = projectDirectory
    val fileLines = ReadFileAsLinesUsingReadLines("") //this will need to change to search for file

    var scenesInBuild = 0




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
        println("Total Scenes in build:" + scenesInBuild)
        println(returnList)

        return returnList
    }




    // File IO operations for build settings file
    // need to change this from hardcoded pathname
    fun ReadFileAsLinesUsingReadLines(fileName: String): List<String>
            = File(directory.toString() + "/ProjectSettings/EditorBuildSettings.ASSET").readLines()


}