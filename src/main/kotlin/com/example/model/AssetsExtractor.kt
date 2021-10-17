package com.example.model

import java.io.File

class AssetsExtractor(projectDirectory: File?) {

    val assetDirectory = projectDirectory?.path + "/Assets"

    lateinit var assetInfo:String

    var  directoryCount:Int = 0



    val imageTypes = AssetType("Images", mutableListOf("png","bmp","tif","tiff","tga","gif","jpg","jpeg","psd","iff","pict","pic","pct","exr","hdr"))
    val audioTypes = AssetType("Audio", mutableListOf("ogg", "aif", "aiff", "flac", "wav", "mp3", "mod", "it", "s3m","xm"))
    val modelTypes = AssetType("Models", mutableListOf("fbx", "mb", "ma", "max", "jas", "dae", "dxf", "obj", "c4d", "blend", "lxo"))
    val nativeTypes = AssetType("Unity Native", mutableListOf("anim", "animset", "asset", "blendtree", "buildreport", "colors", "controller", "cubemap"
        , "curves", "curvesnormalized", "flare", "fontsettings", "giparams", "gradients", "guiskin", "ht", "mask", "mat", "mesh"
        , "mixer", "overridecontroller", "particlecurves", "particlecurvessigned", "particledoublecurves", "particledoublecurvessigned", "physicmaterial", "physicsmaterial2D"
        , "playable", "preset", "rendertexture", "shadervariants", "spriteatlas", "state", "statemachine", "texture2D", "transition", "webcamtexture", "brush", "terrainlayer"
        , "signal")
    )
    val textTypes = AssetType("Text", mutableListOf("txt", "html", "htm", "xml", "json", "csv", "yaml", "bytes", "fnt", "manifest", "md", "js", "boo", "rsp"))

    val prefabType = AssetType("Prefabs", mutableListOf("prefab"))

    val pluginTypes = AssetType("Plugins", mutableListOf("dll", "winmd", "so", "jar", "java", "kt", "aar", "suprx", "prx", "rpl"
        , "cpp", "cc", "c", "h", "jslib", "jspre", "bc", "a", "m", "mm", "swift", "xib", "bundle", "dylib", "config")
    )

    val fontTypes = AssetType("Fonts", mutableListOf("ttf", "dfont", "otf", "ttc"))

    val videoTypes = AssetType("Video", mutableListOf("avi", "asf", "wmv", "mov", "dv", "mp4", "m4v", "mpg", "mpeg", "ogv", "vp8", "webm"))

    val scriptTypes = AssetType("Scripts", mutableListOf("cs"))

    val vfxTypes = AssetType("Visual Effects", mutableListOf("vfx", "vfxoperator", "vfxblock"))

    val shaderTypes = AssetType("Shaders", mutableListOf("cginc", "cg", "glslinc", "hlsl", "shader"))

    val otherTypes = AssetType("Other Types", mutableListOf())

    fun ScanAssetFolder()
    {

        File(assetDirectory).walkTopDown().forEach {

            if(!it.isDirectory) {

                if(imageTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(imageTypes,it.extension)
                }
                else if(audioTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(audioTypes,it.extension)
                }
                else if(modelTypes.types.contains((it.extension).toLowerCase()))
                {
                    AssignType(modelTypes,it.extension)
                }
                else if(nativeTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(nativeTypes,it.extension)
                }
                else if(textTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(textTypes,it.extension)

                }
                else if(prefabType.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(prefabType,it.extension)
                }
                else if(pluginTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(pluginTypes,it.extension)
                }
                else if(fontTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(fontTypes,it.extension)
                }
                else if(videoTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(videoTypes,it.extension)
                }
                else if(scriptTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(scriptTypes,it.extension)
                }
                else if(vfxTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(vfxTypes,it.extension)
                }
                else if(shaderTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(shaderTypes,it.extension)
                }
                else
                {
                    AssignToOtherType(it.extension)
                }
                    //need an add to other type here

            }
            else
            {
                directoryCount++

            }
        }

        println("Total Asset Directories: " + directoryCount)
        println("Audio Types Found: " + audioTypes.typeTotal)

        println("Image Types Found: " + imageTypes.typeTotal)
        PrintResults(imageTypes)

        println("Model Types Found: " + modelTypes.typeTotal)
        PrintResults(modelTypes)

        println("Unity Native Types Found: " + nativeTypes.typeTotal)
        PrintResults(nativeTypes)

        println("Text Types Found: " + textTypes.typeTotal)
        PrintResults(textTypes)

        PrintResults(prefabType)

        println("Other Types Found: " + otherTypes.typeTotal)
        PrintResults(otherTypes)



    }
    fun PrintResults(currentType: AssetType)
    {
        for(i in 0..currentType.types.count()-1)
        {
            println(currentType.types[i] + ": " + currentType.counts[i] )
        }
    }

    fun AssignType(currentType:AssetType, extension:String)
    {
       val lExtension = extension.toLowerCase()

        val index = currentType.types.indexOf(lExtension)
        currentType.counts[index]++
        currentType.typeTotal++
    }

    fun AssignToOtherType(extension: String)
    {
        if(otherTypes.types.contains(extension))
        {
            val index = otherTypes.types.indexOf(extension)
            otherTypes.counts[index]++
        }
        else
        {
            otherTypes.types.add(extension)
            val index = otherTypes.types.indexOf(extension)
            otherTypes.counts[index]++
            otherTypes.typeTotal++
        }
    }

    class AssetType(val name:String, var types:MutableList<String> )
    {
        lateinit var counts:IntArray

        var typeTotal:Int = 0;

        init
        {
            if (types.count() == 0) {
                counts= IntArray(50) {_ -> 0}
            }
            else
            {
                counts = IntArray(types.count()) {_ -> 0}
            }
        }

    }
}
