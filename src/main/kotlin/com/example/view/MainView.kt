package com.example.view

import com.example.Styles
import com.example.controller.MainController
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.control.TabPane
import tornadofx.*


class MainView : View("Unity Scanner Version 0.1") {


    val controller:MainController by inject()

    var versionText = SimpleStringProperty()
    var projectName = SimpleStringProperty()

    var scenesInBuild = SimpleStringProperty();

    var sceneNames = SimpleStringProperty()



    val sceneCountLabel = label()

    //var scenesInBuild = SimpleListProperty<String>()


    override val root = vbox {
        alignment = Pos.TOP_LEFT

        scenesInBuild.set("Scenes in Build: 0")


        menubar {
            menu("File") {
                item("Open Project")
                {
                    setOnAction {

                        controller.OpenProject()
                        versionText.set("Unity Version: " + controller.ExtractVersionNumber())
                        projectName.set("Project Name: " + controller.GetProjectName())
                        scenesInBuild.set("Scenes in Build: " + controller.GetScenesInBuild().count())

                        val scenes = controller.GetScenesInBuild()
                        sceneNames.set(scenes[0])
                    }
                }
                item("Save")
                {

                }
                item("Quit")
                {

                    setOnAction {
                        val alert = Alert(AlertType.CONFIRMATION)
                        alert.title = "Exit Program"
                        alert.headerText = "Exitting Program"
                        alert.contentText = "Are you sure?"

                        val result = alert.showAndWait()
                        if (result.get() == ButtonType.OK) {
                            System.exit(0)
                        }
                    }
                }
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

                scrollpane(fitToHeight = true, fitToWidth = true)
                {
                    vbox {

                            label(scenesInBuild)
                            {
                                bind(scenesInBuild)
                                addClass(Styles.heading)
                            }

                            textarea(sceneNames)
                            {
                                bind(sceneNames)
                                addClass(Styles.heading)

                            }
                    }
                }


            }
            tab("Packages") {

                }
            tab("Assets") {

            }

            }

    }
        }



