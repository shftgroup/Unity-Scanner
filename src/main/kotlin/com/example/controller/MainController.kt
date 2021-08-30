package com.example.controller

import com.example.model.Scanner
import tornadofx.Controller
import tornadofx.chooseDirectory

class MainController: Controller() {

    val mainScanner = Scanner()

    fun GetDirectory()
    {
       mainScanner.GetDirectory()
    }


    fun ExtractVersionNumber():String
    {
        return mainScanner.ExtractVersionNumber()
    }

}