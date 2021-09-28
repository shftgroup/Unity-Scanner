package com.example.model

import tornadofx.JsonModel
import java.io.File

import com.google.gson.Gson

class PackageManifest() {

    val packageDirectory = "C:/Users/jsj59/Documents/GitHub/Dungeon-Escape/Library/PackageCache"

    var packageList = mutableListOf<String>()

    var packages = mutableListOf<UnityPackage>()



    fun LoadManifest(filename:String)
    {

        FindPackageFiles()

        //  val jsonFile = File("C:/Users/jsj59/Documents/GitHub/Dungeon-Escape/Library/PackageCache/com.unity.mathematics@1.1.0/package.json")
        //val jsonFile =
         //   File("C:/Users/jsj59/Documents/GitHub/Dungeon-Escape/Library/PackageCache/com.unity.2d.animation@5.0.6/package.json")

        val gson = Gson()
        ////////////new code block for loading


        for(packageName in packageList)
        {
           // println(packageName)
           var jsonFile = File(packageName)
           var jsonString = jsonFile.readText()
            //print(jsonString)
            //load single package here
            //and add it to packages
            var singlePackage = gson.fromJson(jsonString, UnityPackage::class.java);

            var rawDependencies = gson.fromJson(jsonString, DependencyLoader::class.java)
            singlePackage.dependendies = ExtractDependencies(rawDependencies)

            //println(singlePackage)

            packages.add(singlePackage)
            println(packages[0])
        }

    }

    private fun ExtractDependencies(dependencies:DependencyLoader): String {

        if(dependencies.dependencies == null)
            return ""

        var returnValue = String()

        var deps = dependencies.dependencies.toString()



        deps = deps.substring(1, deps.lastIndex)
       // println(deps)
        val splitDeps = deps.split(",")

        for (line in splitDeps) {
            var nameVersion = line.split("=").toMutableList()
            if(nameVersion.count() > 1) {
                //trim white space if needed from start of string
                if(nameVersion[0].startsWith(" ")) {
                    nameVersion[0] = nameVersion[0].substring(1, nameVersion[0].lastIndex)
                }
                returnValue += nameVersion[0] + " " + nameVersion[1] + "\n"
                //println(returnValue)
            }
        }

        return returnValue
    }


    fun FindPackageFiles()
        {
            File(packageDirectory).walkTopDown().forEach {
                if (it.name == "package.json") {
                   //println(it)
                    packageList.add(it.path)
                }
                //print(packageList)
            }

    }



        data class UnityPackage(
            val name: String, val version: String, val unity: String,
            val displayName: String, val description: String,
            val repository: PackageRepository,
            var dependendies:String
        )

        data class PackageRepository(val url: String, val type: String, val revision: String) {}

        data class DependencyLoader(val dependencies: Object) {}


}


