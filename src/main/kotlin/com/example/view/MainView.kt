package com.example.view

import com.example.Styles
import com.example.model.Scanner
import javafx.scene.control.TabPane
import tornadofx.*


class MainView : View("Hello TornadoFX") {

    lateinit var projectScanner:Scanner

    override val root = vbox {
        menubar {
            menu("File") {
                item("Open Project")
                {
                    setOnAction { projectScanner = Scanner() }
                }
                item("Save")
                item("Quit")
            }
            menu("Edit") {
                item("Copy")
                item("Paste")
            }
        }

        label("Editor Version: ") {
            addClass(Styles.heading)
        }
        tabpane {


            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

            tab("Build") {
                disableClose()
            }
            tab("Packages") {

                }
            tab("Assets") {

            }

            }

    }
        }



