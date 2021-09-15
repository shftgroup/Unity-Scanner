package com.example.view

import com.example.Styles
import com.example.controller.MainController
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonBar
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

                        var sceneNameString = ""

                        for(x in 0..scenes.count()-1)
                        {
                            sceneNameString += scenes[x] + "\n"
                            println(scenes[x])
                        }

                        sceneNames.set(sceneNameString)
                    }
                }
                item("Save")
                {

                }
                item("Quit")
                {

                    setOnAction {

                        alert(
                            type = Alert.AlertType.CONFIRMATION,
                            header = "Delete User",
                            content = "Delete User ",
                            actionFn = {
                                    btnType ->
                                if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                   System.exit(0)
                                }
                            }
                        )
                    }
                }
            }
/*
            menu("Edit") {
                item("Copy")
                item("Paste")
            }

 */
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
                                addClass(Styles.textArea)
                                isEditable = false

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



