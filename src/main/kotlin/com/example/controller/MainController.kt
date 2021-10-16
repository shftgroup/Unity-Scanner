package com.example.controller

import com.example.main
import com.example.model.PackageManifest
import com.example.model.Scanner
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
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

    fun GetPackages(): ObservableList<PackageManifest.UnityPackage>
    {

        val packageNamesList: ObservableList<PackageManifest.UnityPackage> = FXCollections.observableArrayList()

        for(singlePackage in mainScanner.packageList.packages)
        {
            packageNamesList.add(singlePackage)
        }

        return packageNamesList

    }

    fun GetAssetInfo():String
    {
        return ""
    }
}