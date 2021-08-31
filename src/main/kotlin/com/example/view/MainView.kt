package com.example.view

import com.example.Styles
import com.example.controller.MainController
import com.example.model.Scanner
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.TabPane
import tornadofx.*


class MainView : View("Unity Scanner Version 0.1") {


    val controller:MainController by inject()

    var versionText = SimpleStringProperty()
    var projectName = SimpleStringProperty()

    override val root = vbox {
        alignment = Pos.TOP_LEFT

        menubar {
            menu("File") {
                item("Open Project")
                {
                    setOnAction {

                        controller.OpenProject()
                        versionText.set( "Unity Version: " + controller.ExtractVersionNumber())
                        projectName.set("Project Name: " + controller.GetProjectName())
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

        hbox {

            label(versionText) {
                bind(versionText)
                addClass(Styles.heading)
            }

            label(projectName) {
                bind(projectName)
                addClass(Styles.heading)
                alignment = Pos.BASELINE_RIGHT
            }

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



