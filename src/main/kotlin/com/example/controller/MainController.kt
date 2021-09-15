package com.example.controller

import com.example.model.Scanner
import javafx.beans.property.SimpleListProperty
import tornadofx.Controller
import tornadofx.chooseDirectory

class MainController: Controller() {

    val mainScanner = Scanner()

    fun OpenProject()
    {
       mainScanner.OpenProject()
    }


    fun ExtractVersionNumber():String
    {
        return mainScanner.ExtractVersionNumber()
    }
    fun GetProjectName():String{
        return mainScanner.projectName;
    }
    fun GetScenesInBuild():List<String>
    {
        return mainScanner.sceneExtractor.ExtractScenesInBuild()
    }
    fun GetTotalScenesInAssets():List<String>
    {
        return mainScanner.sceneExtractor.ExtractAllScenesFromAssets()
    }

}