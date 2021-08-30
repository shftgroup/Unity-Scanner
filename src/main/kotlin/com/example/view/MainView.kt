package com.example.view

import com.example.Styles
import com.example.controller.MainController
import com.example.model.Scanner
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.TabPane
import tornadofx.*


class MainView : View("Hello TornadoFX") {


    val controller:MainController by inject()

    var versionText = SimpleStringProperty()

    override val root = vbox {
        alignment = Pos.TOP_LEFT

        menubar {
            menu("File") {
                item("Open Project")
                {
                    setOnAction {

                        controller.GetDirectory()
                       versionText.set( "Unity Version: " + controller.ExtractVersionNumber())
                    }
                }
                item("Save")
                {

                }
                item("Quit")
            }
            menu("Edit") {
                item("Copy")
                item("Paste")
            }
        }

        label(versionText) {
            bind(versionText)
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



