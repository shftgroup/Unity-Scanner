package com.example.view

import com.example.Styles
import com.example.controller.MainController
import com.example.model.PackageManifest
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.TabPane
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.media.AudioClip
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.FileChooser
import sun.security.rsa.RSAUtil
import tornadofx.*
import java.awt.event.KeyEvent
import java.io.File
import java.io.FileInputStream



class MainView : View("Unity Scanner Version 1.0") {



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

    var assetInfo = SimpleStringProperty()

    var imageList:ObservableList<String> = FXCollections.observableArrayList()
    var imagePaths = mutableListOf<String>()
    var currentImage = SimpleObjectProperty<Image>()
    var currentImageInfo = SimpleStringProperty()

    var textList:ObservableList<String> = FXCollections.observableArrayList()
    var currentText = SimpleStringProperty()
    var textPaths = mutableListOf<String>()

    var scriptList:ObservableList<String> = FXCollections.observableArrayList()
    var scriptPaths = mutableListOf<String>()
    var currentScript = SimpleStringProperty()

    var audioList:ObservableList<String> = FXCollections.observableArrayList()
    var audioPaths = mutableListOf<String>()
    lateinit var audioClip: AudioClip

    var nativeList:ObservableList<String> = FXCollections.observableArrayList()
    var nativePaths = mutableListOf<String>()
    var currentNativeItem = SimpleStringProperty()

    var modelList:ObservableList<String> = FXCollections.observableArrayList()
    var modelPaths = mutableListOf<String>()
    var currentModel = SimpleStringProperty()

    var prefabList:ObservableList<String> = FXCollections.observableArrayList()
    var prefabPaths = mutableListOf<String>()
    var currentPrefab = SimpleStringProperty()

    var shaderList:ObservableList<String> = FXCollections.observableArrayList()
    var shaderPaths = mutableListOf<String>()
    var currentShader = SimpleStringProperty()

    var vfxList:ObservableList<String> = FXCollections.observableArrayList()
    var vfxPaths = mutableListOf<String>()
    var currentVfx = SimpleStringProperty()

    var fontList:ObservableList<String> = FXCollections.observableArrayList()
    var fontPaths = mutableListOf<String>()
    var currentFont = javafx.scene.text.Font.getDefault()
    var fontText = SimpleStringProperty("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

    var reportText = SimpleStringProperty()

    var projectOpen = false;

    var TA = javafx.scene.control.TextArea()
    lateinit var TA2:javafx.scene.control.Label

    var currentIndex = 0

    override val root = vbox {
        alignment = Pos.TOP_LEFT

        scenesInBuild.set("Scenes in Build: 0")


        TA.font = javafx.scene.text.Font.getDefault()
//


        menubar {
            menu("File") {
                item("Open Project")
                {
                    setOnAction {

                        controller.mainScanner.ClearData()
                        ClearLists()

                        reportText.set("")

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

                        if(projectPackages.count() > 0)
                            currentPackageInfo.set("")

                        assetInfo.set(controller.GetAssetInfo())

                        reportText.set(controller.mainScanner.report)

                        for(singlePackage in projectPackages)
                        {
                            packageNames.add(singlePackage.name)
                        }

                        for(imageName in controller.GetImageList())
                        {
                            imageList.add(imageName.substringAfterLast('/').substringAfterLast('\\'))
                            imagePaths.add(imageName)
                        }
                        for(text in controller.mainScanner.assets.textList)
                        {
                            textList.add(text.substringAfterLast('/').substringAfterLast('\\'))
                            textPaths.add(text)
                        }
                        for(sound in controller.mainScanner.assets.audioList)
                        {
                            audioList.add(sound.substringAfterLast('/').substringAfterLast('\\'))
                            audioPaths.add(sound)
                        }
                        for(script in controller.mainScanner.assets.scriptList)
                        {
                            scriptList.add(script.substringAfterLast('/').substringAfterLast('\\'))
                            scriptPaths.add(script)
                        }
                        for(native in controller.mainScanner.assets.nativeList)
                        {
                            nativeList.add(native.substringAfterLast('/').substringAfterLast('\\'))
                            nativePaths.add(native)
                        }
                        for(model in controller.mainScanner.assets.modelList)
                        {
                            modelList.add(model.substringAfterLast('/').substringAfterLast('\\'))
                            modelPaths.add(model)
                        }
                        for(prefab in controller.mainScanner.assets.prefabList)
                        {
                            prefabList.add(prefab.substringAfterLast('/').substringAfterLast('\\'))
                            prefabPaths.add(prefab)
                        }
                        for(shader in controller.mainScanner.assets.shaderList)
                        {
                            shaderList.add(shader.substringAfterLast('/').substringAfterLast('\\'))
                            shaderPaths.add(shader)
                        }
                        for(vfx in controller.mainScanner.assets.vfxList)
                        {
                            vfxList.add(vfx.substringAfterLast('/').substringAfterLast('\\'))
                            vfxPaths.add(vfx)
                        }
                        for(font in controller.mainScanner.assets.fontList)
                        {
                            fontList.add(font.substringAfterLast('/').substringAfterLast('\\'))
                            fontPaths.add(font)
                        }

                        projectOpen = true;
                    }

                }
                item("Save")
                {
                    setOnAction {

                        if(projectOpen == true) {

                            val fc = FileChooser()
                            fc.extensionFilters.addAll(FileChooser.ExtensionFilter("Text Files", "*.txt"))
                            fc.initialDirectory = controller.mainScanner.directory
                            val fileName = fc.showSaveDialog(currentStage)

                            if(fileName != null) {
/*
                            val fileName2 = chooseFile(
                                "Save Project Report",
                                arrayOf(FileChooser.ExtensionFilter("All Files", listOf("*.*"))),
                                controller.mainScanner.directory,
                                FileChooserMode.Save,
                                null
                            ) { }
*/
                                print(fileName)

                                fileName.writeText(controller.mainScanner.report, Charsets.UTF_8)
/*
                                if (fileName.exists()) {
                                    fileName.writeText(controller.mainScanner.report, Charsets.UTF_8)
                                } else {
                                    fileName.createNewFile()
                                    fileName.writeText(controller.mainScanner.report, Charsets.UTF_8)
                                }*/
                            }
                        }
                        else
                        {
                            alert(
                                type = Alert.AlertType.ERROR,
                                header = "No Project Open",
                                content = "You need to open a project first!",
                                actionFn = {
                                        btnType ->
                                    if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {

                                    }
                                }
                            )
                        }

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
                                prefHeight = 650.0
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
                                prefHeight = 650.0
                                addClass(Styles.textArea)
                                isEditable = false
                            }
                        }


                }



            }
            tab("Packages") {



                hbox{

                    try{
                    listview(values = packageNames)
                    {
                        addClass(Styles.textArea)
                       prefWidth = 500.0
                        prefHeight = 650.0
                        setOnMouseClicked() {
                            val index = this.selectionModel.selectedIndex;

                            lateinit var packageDetails:PackageManifest.UnityPackage

                            println("Click! on Index " + index)

                            try{


                                    if(projectPackages.count() > 0)
                                    {

                                    if(index >=0 && index < projectPackages.count())
                                    {
                                        packageDetails = projectPackages[index]
                                        }
                                        var detailsString = "Package Details:\n"

                                        println(packageDetails.name)
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
                            catch(e:Exception)
                            {

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
                        prefHeight = 650.0
                        //setMaxSize((prefWidth),(prefHeight))
                        bind(currentPackageInfo)

                        val v = controller.mainScanner.editorVersion.substringBefore('.').toInt()
                        print("Version: " + v)
                        if(v  <= 2017)
                        {
                            println("OLD VERSION DETECTED")
                            currentPackageInfo.set("Package Manager is not availabe prior to Unity 2018")

                        }

                    }
                }


                }
            tab("Assets") {
                tabpane {
                    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

                        tab("Asset Summary")
                        {
                            prefHeight = 650.0
                            hbox {
                                textarea(assetInfo)
                                {
                                    bind(assetInfo)
                                    addClass(Styles.textArea)
                                    isEditable = false

                                }


                            }
                        }
                        tab("Images")
                        {
                            hbox {
                                listview(values = imageList)
                                {
                                    addClass(Styles.textArea)
                                    prefWidth = 600.0
                                    prefHeight = 600.0
                                    setOnMouseClicked() {
                                            currentIndex = this.selectionModel.selectedIndex;

                                            if((currentIndex != -1) && (imagePaths.count() > 0))
                                            {
                                                println("Click! on Index " + currentIndex)
                                                UpdateImage()
                                            }
                                        }
                                    setOnKeyPressed {
                                       if(it.code == KeyCode.DOWN)
                                       {
                                           DownPressed(imagePaths.count())

                                           if(imagePaths.count() > 0)
                                           {
                                              UpdateImage()
                                           }
                                       }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(imagePaths.count() > 0)
                                            {
                                                UpdateImage()
                                            }
                                        }
                                    }
                                }
                                vbox {
                                    imageview(currentImage)
                                    {

                                        fitHeight = 600.0
                                        fitWidth = 600.0

                                        setPreserveRatio(true)
                                    }

                                    textarea()
                                    {

                                        isEditable = false
                                        prefWidth = 1200.0
                                        prefHeight = 200.0

                                        addClass(Styles.heading)
                                        bind(currentImageInfo)
                                    }
                                }
                            }
                        }
                        tab("Text")
                        {

                            hbox {
                                listview(values = textList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0
                                    setOnMouseClicked() {
                                       currentIndex = this.selectionModel.selectedIndex;
                                        if((currentIndex != -1) && (textPaths.count() > 0))
                                        {
                                            println("Click! on Index " + currentIndex)
                                            UpdateText(currentText,textPaths[currentIndex])
                                        }
                                    }
                                    setOnKeyPressed{

                                        if(it.code == KeyCode.DOWN)
                                        {
                                            DownPressed(textPaths.count())

                                            if(textPaths.count() > 0)
                                            {
                                                UpdateText(currentText, textPaths[currentIndex])
                                            }
                                        }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(textPaths.count() > 0)
                                            {
                                                UpdateText(currentText, textPaths[currentIndex])
                                            }
                                        }
                                    }

                                }
                                textarea(currentText) {
                                    bind(currentText)
                                    prefWidth = 1200.0
                                    prefHeight = 650.0
                                    isEditable = false

                                }
                            }
                        }
                        tab("Audio")
                        {
                            hbox {
                                listview(values = audioList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0


                                    setOnMouseClicked() {
                                            currentIndex = this.selectionModel.selectedIndex;
                                            if((currentIndex != -1) && (audioPaths.count() > 0))
                                            {
                                                println("Click! on Index " + currentIndex)
                                                audioClip = AudioClip(File(audioPaths[currentIndex]).toURI().toString())
                                            }

                                        }
                                    setOnKeyPressed{

                                        if(it.code == KeyCode.DOWN)
                                        {
                                            DownPressed(audioPaths.count())

                                            if(audioPaths.count() > 0)
                                            {
                                                audioClip = AudioClip(File(audioPaths[currentIndex]).toURI().toString())
                                            }
                                        }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(audioPaths.count() > 0)
                                            {
                                                audioClip = AudioClip(File(audioPaths[currentIndex]).toURI().toString())
                                            }
                                        }
                                    }

                                }

                                hbox {

                                    button("Play Sound")
                                    {
                                        hboxConstraints {
                                            marginTop = 225.0
                                            marginLeft = 400.0

                                        }
                                        style {
                                            padding = box(20.px)


                                        }
                                        setOnAction {

                                            audioClip.play()
                                        }
                                    }
                                    button("Stop Sound")
                                    {
                                        hboxConstraints {
                                            marginTop = 225.0
                                            marginLeft = 100.0

                                        }
                                        style {
                                            padding = box(20.px)


                                        }
                                        setOnAction {

                                            audioClip.stop()
                                        }
                                    }

                                }


                            }
                        }
                        tab("Scripts")
                        {
                            hbox {
                                listview(values = scriptList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0


                                    setOnMouseClicked() {
                                        currentIndex = this.selectionModel.selectedIndex;
                                        if((currentIndex != -1) && (scriptPaths.count() > 0))
                                        {
                                            println("Click! on Index " + currentIndex)
                                            UpdateText(currentScript,scriptPaths[currentIndex])
                                        }
                                        }
                                    setOnKeyPressed{

                                        if(it.code == KeyCode.DOWN)
                                        {
                                            DownPressed(scriptPaths.count())

                                            if(scriptPaths.count() > 0)
                                            {
                                                UpdateText(currentScript,scriptPaths[currentIndex])
                                            }
                                        }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(scriptPaths.count() > 0)
                                            {
                                                UpdateText(currentScript,scriptPaths[currentIndex])
                                            }
                                        }
                                    }

                                }
                                textarea(currentScript) {
                                    bind(currentScript)
                                    prefWidth = 1200.0
                                    prefHeight = 600.0

                                    isEditable = false
                                }
                            }
                        }
                        tab("Unity Native Files")
                        {
                            hbox {
                                listview(values = nativeList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0


                                    setOnMouseClicked() {

                                        currentIndex = this.selectionModel.selectedIndex;
                                        if((currentIndex != -1) && (nativePaths.count() > 0))
                                        {
                                            println("Click! on Index " + currentIndex)
                                            UpdateText(currentNativeItem,nativePaths[currentIndex])
                                        }

                                        }
                                    setOnKeyPressed{

                                        if(it.code == KeyCode.DOWN)
                                        {
                                            DownPressed(nativePaths.count())

                                            if(nativePaths.count() > 0)
                                            {
                                                UpdateText(currentNativeItem,nativePaths[currentIndex])
                                            }
                                        }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(nativePaths.count() > 0)
                                            {
                                                UpdateText(currentNativeItem,nativePaths[currentIndex])
                                            }
                                        }
                                    }

                                }
                                textarea(currentNativeItem) {
                                    bind(currentNativeItem)
                                    prefWidth = 1200.0
                                    prefHeight = 650.0

                                    isEditable = false


                                }

                            }
                        }
                        /*  tab("Models")
                    {
                        hbox {
                            listview(values = modelList)
                            {
                                addClass(Styles.textArea)

                                prefWidth = 600.0

                                try {
                                    setOnMouseClicked() {
                                        val index = this.selectionModel.selectedIndex;

                                        println("Click! on Index " + index)

                                        //change for text files
                                        val fileName = modelPaths[index]

                                        currentModel.set( File(fileName).readText())


                                    }
                                }
                                catch(e:Exception) {
                                }

                            }
                            textarea(currentModel) {
                                bind(currentModel)
                                prefWidth = 600.0
                                prefHeight = 650.0

                                isEditable = false


                            }

                        }
                    }
                    */
                        tab("Prefabs")
                        {
                            hbox {
                                listview(values = prefabList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0


                                    setOnMouseClicked() {

                                        currentIndex = this.selectionModel.selectedIndex;
                                        if((currentIndex != -1) && (prefabPaths.count() > 0))
                                        {
                                            println("Click! on Index " + currentIndex)
                                            UpdateText(currentPrefab,prefabPaths[currentIndex])
                                        }

                                        }
                                    setOnKeyPressed{

                                        if(it.code == KeyCode.DOWN)
                                        {
                                            DownPressed(prefabPaths.count())

                                            if(prefabPaths.count() > 0)
                                            {
                                                UpdateText(currentPrefab,prefabPaths[currentIndex])
                                            }
                                        }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(prefabPaths.count() > 0)
                                            {
                                                UpdateText(currentPrefab,prefabPaths[currentIndex])
                                            }
                                        }
                                    }


                                }
                                textarea(currentPrefab) {
                                    bind(currentPrefab)
                                    prefWidth = 1200.0
                                    prefHeight = 650.0

                                    isEditable = false


                                }

                            }
                        }
                        tab("Shaders")
                        {
                            hbox {
                                listview(values = shaderList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0


                                    setOnMouseClicked() {

                                        currentIndex = this.selectionModel.selectedIndex;
                                        if((currentIndex != -1) && (shaderPaths.count() > 0))
                                        {
                                            println("Click! on Index " + currentIndex)
                                            UpdateText(currentShader,shaderPaths[currentIndex])
                                        }

                                        }
                                    setOnKeyPressed{

                                        if(it.code == KeyCode.DOWN)
                                        {
                                            DownPressed(shaderPaths.count())

                                            if(shaderPaths.count() > 0)
                                            {
                                                UpdateText(currentShader,shaderPaths[currentIndex])
                                            }
                                        }
                                        if(it.code == KeyCode.UP)
                                        {
                                            UpPressed()
                                            if(shaderPaths.count() > 0)
                                            {
                                                UpdateText(currentShader,shaderPaths[currentIndex])
                                            }
                                        }
                                    }

                                }
                                textarea(currentShader) {
                                    bind(currentShader)
                                    prefWidth = 1200.0
                                    prefHeight = 650.0

                                    isEditable = false


                                }

                            }
                        }
                        tab("VFX")
                        {
                            hbox {
                                listview(values = vfxList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0

                                    try {
                                        setOnMouseClicked() {
                                            val index = this.selectionModel.selectedIndex;

                                            println("Click! on Index " + index)

                                            //change for text files
                                            val fileName = vfxPaths[index]

                                            currentVfx.set(File(fileName).readText())


                                        }
                                    } catch (e: Exception) {
                                    }

                                }
                                textarea(currentVfx) {
                                    bind(currentVfx)
                                    prefWidth = 1200.0
                                    prefHeight = 650.0

                                    isEditable = false


                                }

                            }
                        }
                        tab("Fonts")
                        {
                            hbox {
                                listview(values = fontList)
                                {
                                    addClass(Styles.textArea)

                                    prefWidth = 600.0

                                    try {
                                        setOnMouseClicked() {
                                            val index = this.selectionModel.selectedIndex;

                                            println("Click! on Index " + index)

                                            val fonts = File(fontPaths[index])
                                            val fontFile = FileInputStream(fonts)

                                            currentFont = javafx.scene.text.Font.loadFont(fontFile, 24.0)

                                            if (currentFont != null) {


                                                TA2.fontProperty().set(currentFont)

                                                println(fontPaths[index])
                                                fontText.set("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

                                            } else {
                                                println("Current Font is NUll")
                                            }

                                        }
                                    } catch (e: Exception) {
                                    }

                                }

                                hbox {
                                    hboxConstraints {
                                        // marginTop = 175.0
                                        marginLeft = 50.0

                                    }

                                    style {
                                        padding = box(20.px)


                                    }

                                    TA2 = label(fontText) {

                                        bind(fontText)
                                        prefWidth = 1200.0
                                        prefHeight = 650.0

                                        font = javafx.scene.text.Font.getDefault()


                                        //isEditable = false


                                    }
                                }

                            }
                        }


                        tab("Report")
                        {
                            textarea {
                                addClass(Styles.heading)

                                isEditable = true

                                bind(reportText)


                            }


                        }
                        /*tab("Info")
            {
                tabpane {


                    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

                    tab("FAQ")
                    {
                        textflow {
                            val lines = File("Info.txt").readLines()
                            println("Lines in file: " + lines.count())

                            var index = 0


                                    vbox {
                                        for (index in 0 until lines.count()) {
                                            println(lines[index])
                                            label(lines[index])
                                            {
                                                addClass(Styles.heading)
                                            }
                                            text(lines[index] + "\n")
                                            {
                                                fill = Color.GREEN
                                                font = Font(15.0)
                                            }
                                        }
                                    }

                        }

                    }
                }











            }*/


                }

        }

        }

    }

    fun  ClearLists()
    {
        projectPackages.clear()

        currentPackageInfo.set("Package Manager is not availabe prior to Unity 2018")
      //  val v = controller.mainScanner.editorVersion.substringBefore('.').toInt()
      //  print("Version: " + v)
       // if(v  <= 2017)
       // {
        //    println("OLD VERSION DETECTED")
        //    currentPackageInfo.set("Package Manager is not availabe prior to Unity 2018")

       // }

        packageNames.clear()

        imageList.clear()
        imagePaths.clear()


        textList.clear()

        textPaths.clear()

        scriptList.clear()
        scriptPaths.clear()


        audioList.clear()
        audioPaths.clear()

        nativeList.clear()
        nativePaths.clear()
        modelList.clear()
        modelPaths.clear()

        prefabList.clear()
        prefabPaths.clear()

        shaderList.clear()
        shaderPaths.clear()

        fontList.clear()
        fontPaths.clear()

    }


    fun UpPressed()
    {
        currentIndex -= 1
        if(currentIndex < 0)
        {
            currentIndex = 0
            println("Count: " + imagePaths.count() + " Index: " + currentIndex)
        }
    }

    fun DownPressed(length:Int)
    {
        currentIndex += 1
        if(currentIndex > length - 1)
        {
            currentIndex -= 1

            if(currentIndex < 0)
            {
                currentIndex = 0
            }

            println("Count: " + length + " Index: " + currentIndex)
        }
    }

    fun UpdateImage()
    {
        val fileStream = FileInputStream(imagePaths[currentIndex])
        val image = Image(fileStream)
        currentImage.set(image)

        currentImageInfo.set("")

        val imageInfo =
            "Image Size: " + image.width + " X " + image.height + "\n" + "Path: " + imagePaths[currentIndex]


        currentImageInfo.set(imageInfo)
    }
    fun UpdateText(text:SimpleStringProperty, fileName:String)
    {
        //change for text files
       // val fileName = textPaths[currentIndex]

       // currentText.set(File(fileName).readText())
        text.set((File(fileName).readText()))

    }





}





