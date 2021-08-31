package com.example.controller

import com.example.model.Scanner
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

}