package com.example.view

import com.example.Styles
import com.example.controller.MainController
import com.example.model.PackageManifest
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.TabPane
import tornadofx.*
import java.io.File
import java.lang.Exception
import javax.json.JsonObject
import javax.swing.plaf.synth.Region


class MainView : View("Unity Scanner Version 0.1") {



    val controller:MainController by inject()

    var versionText = SimpleStringProperty()
    var projectName = SimpleStringProperty()

    var scenesInBuild = SimpleStringProperty();
    var sceneNames = SimpleStringProperty()

    var totalScenesInAssetsList = SimpleStringProperty()
    var totalSceneNamesCount = SimpleStringProperty()

    val sceneCountLabel = label()


    var projectPackages: ObservableList<PackageManifest.UnityPackage> = FXCollections.observableArrayList()
   // lateinit var packageNamesList: ObservableList<PackageManifest.UnityPackage>
    var packageNames:ObservableList<String> = FXCollections.observableArrayList()
    var currentPackageInfo = SimpleStringProperty()

    //now ready to populate UI
    //var jpk = PackageManifest()


    //var scenesInBuild = SimpleListProperty<String>()


    override val root = vbox {
        alignment = Pos.TOP_LEFT

        scenesInBuild.set("Scenes in Build: 0")
////////////////////////////////
      //  val file = File("C:/Users/jsj59/Documents/GitHub/Dungeon-Escape/Library/PackageCache/com.unity.mathematics@1.1.0/package.json")

     //  val pm = PackageManifest()
     //   pm.LoadManifest("None")
        //this code needs to move down to the open project menu item


        //val jsonFile = File("C:/Users/jsj59/Documents/GitHub/Dungeon-Escape/Library/PackageCache/com.unity.mathematics@1.1.0").readText()
//////////////////////////////////////////////


        menubar {
            menu("File") {
                item("Open Project")
                {
                    setOnAction {

                        controller.OpenProject()
                        versionText.set("Unity Version: " + controller.ExtractVersionNumber())
                        projectName.set("Project Name: " + controller.GetProjectName())



                        var scenes = controller.GetScenesInBuild()
                        scenesInBuild.set("Scenes in Build: " +scenes.count())

                        var sceneNameString = ""

                        for(x in 0..scenes.count()-1)
                        {
                            sceneNameString += scenes[x] + "\n"
                           // println(scenes[x])
                        }

                        sceneNames.set(sceneNameString)

                        sceneNameString = ""

                        scenes = controller.GetTotalScenesInAssets()
                        totalSceneNamesCount.set("Total Scenes in Asset Folder: " + scenes.count())


                        for(x in 0..scenes.count()-1)
                        {
                            sceneNameString += scenes[x] + "\n"
                        }

                        totalScenesInAssetsList.set(sceneNameString)

                        projectPackages = controller?.GetPackages()



                        for(singlePackage in projectPackages)
                        {
                            packageNames.add(singlePackage.name)
                        }

                    }
                }
                item("Save")
                {
                    setOnAction {

                    /*
                       packageNamesList.add("Cinemachine")
                        packageNamesList.add("math")
                        packageNamesList.add("animation")
                        packageNamesList.add("2d")
*/



                    }

                }
                item("Quit")
                {

                    setOnAction {

                        alert(
                            type = Alert.AlertType.CONFIRMATION,
                            header = "Exit Program",
                            content = "Do you want to exit?",
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
                    hbox {
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
                        vbox {
                            label(totalSceneNamesCount)
                            {
                                bind(totalSceneNamesCount)
                                addClass(Styles.heading)

                            }
                            textarea(totalScenesInAssetsList){
                                bind(totalScenesInAssetsList)
                                addClass(Styles.textArea)
                                isEditable = false
                            }
                        }
                    }

                }



            }
            tab("Packages") {

                hbox{

                    try{
                    listview(values = packageNames)
                    {

                       //prefWidth = 500.0
                        setOnMouseClicked() {
                            val index = this.selectionModel.selectedIndex;

                            lateinit var packageDetails:PackageManifest.UnityPackage

                            println("Click! on Index " + index)

                            if(projectPackages.count() > 0){

                                if(index >=0 && index < projectPackages.count()) {
                                    packageDetails = projectPackages[index]
                                }
                                var detailsString = "Package Details:\n"


                                detailsString += "Package Name: " + packageDetails.name + "\n"
                                detailsString += "Package Version: " + packageDetails.version + "\n"
                                detailsString += "Lowest Compatible Unity Version: " + packageDetails.unity + "\n"
                                detailsString += "Display name: " + packageDetails.displayName + "\n"
                                detailsString += "Package Details: " + packageDetails.description + "\n\n"

                                detailsString += "Source Code Repository Info:\n"

                                if(packageDetails.repository != null) {
                                    detailsString += "VCS used: " + packageDetails.repository.type + "\n"
                                    detailsString += "Repository URL: " + packageDetails.repository.url + "\n"
                                    detailsString += "Repository Revision: " + packageDetails.repository.revision + "\n\n"
                                }
                                else{
                                    detailsString += "None\n\n"
                                }

                                if(packageDetails.dependendies.count() > 0)
                                {
                                detailsString += "Dependencies:\n"
                                detailsString += packageDetails.dependendies.toString() + "\n\n"

                                }
                                else
                                {
                                    detailsString += "No dependencies were listed for this package\n\n"
                                }

                                detailsString += "Total Packages: " + projectPackages.count()

                            currentPackageInfo.set(detailsString)
                            }
                        }
                    }
                    }
                    catch(e:Exception)
                    {

                    }
                    textarea()
                    {
                        prefWidth = 1200.0
                        prefHeight = 600.0
                        //setMaxSize((prefWidth),(prefHeight))
                        bind(currentPackageInfo)

                    }
                }


                }
            tab("Assets") {

            }

            }

    }
        }



