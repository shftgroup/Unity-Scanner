package com.example.model

import java.io.File

class AssetsExtractor(projectDirectory: File?) {

    val assetDirectory = projectDirectory?.path + "/Assets"

    var assetInfo:String = ""

    var  directoryCount:Int = 0

    var imageList = mutableListOf<String>()

    var textList = mutableListOf<String>()

    var audioList = mutableListOf<String>()

    var scriptList = mutableListOf<String>()

    var nativeList = mutableListOf<String>()

    var modelList = mutableListOf<String>()

    var prefabList = mutableListOf<String>()

    var shaderList = mutableListOf<String>()

    var vfxList = mutableListOf<String>()

    var fontList = mutableListOf<String>()

    val imageTypes = AssetType("Images", mutableListOf("png","bmp","tif","tiff","tga","gif","jpg","jpeg","iff","pict","pic","pct","exr","hdr","psd"))
    val audioTypes = AssetType("Audio", mutableListOf(/*"ogg",*/ "aif", "aiff", "flac", "wav", "mp3", "mod", "it", "s3m","xm"))
    val modelTypes = AssetType("Models", mutableListOf("fbx", "mb", "ma", "max", "jas", "dae", "dxf", "obj", "c4d", "blend", "lxo"))
    val nativeTypes = AssetType("Unity Native", mutableListOf("anim", "animset", "asset", "blendtree", "buildreport", "colors", "controller", "cubemap"
        , "curves", "curvesnormalized", "flare", "fontsettings", "giparams", "gradients", "guiskin", "ht", "mask", "mat", "mesh"
        , "mixer", "overridecontroller", "particlecurves", "particlecurvessigned", "particledoublecurves", "particledoublecurvessigned", "physicmaterial", "physicsmaterial2D"
        , "playable", "preset", "rendertexture", "shadervariants", "spriteatlas", "state", "statemachine", "texture2D", "transition", "webcamtexture", "brush", "terrainlayer"
        , "signal","physicsmaterial2d","unity")
    )
    val textTypes = AssetType("Text", mutableListOf("txt", "html", "htm", "xml", "json", "csv", "yaml", "bytes", "fnt", "manifest", "md", "boo", "rsp"))

    val prefabType = AssetType("Prefabs", mutableListOf("prefab"))

    val pluginTypes = AssetType("Plugins", mutableListOf("dll", "winmd", "so", "jar", "java", "kt", "aar", "suprx", "prx", "rpl"
        , "cpp", "cc", "c", "h", "jslib", "jspre", "bc", "a", "m", "mm", "swift", "xib", "bundle", "dylib", "config")
    )

    val fontTypes = AssetType("Fonts", mutableListOf("ttf", "dfont", "otf", "ttc"))

    val videoTypes = AssetType("Video", mutableListOf("avi", "asf", "wmv", "mov", "dv", "mp4", "m4v", "mpg", "mpeg", "ogv", "vp8", "webm"))

    val scriptTypes = AssetType("Scripts", mutableListOf("cs","js"))

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
                    imageList.add(it.path)

                }
                else if(audioTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(audioTypes,it.extension)
                    audioList.add(it.path)
                }
                else if(modelTypes.types.contains((it.extension).toLowerCase()))
                {
                    AssignType(modelTypes,it.extension)
                    modelList.add(it.path)
                }
                else if(nativeTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(nativeTypes,it.extension)
                    nativeList.add(it.path)
                }
                else if(textTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(textTypes,it.extension)
                    textList.add(it.path)
                }
                else if(prefabType.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(prefabType,it.extension)
                    prefabList.add(it.path)
                }
                else if(pluginTypes.types.contains((it.extension.toLowerCase())))
                {
                    AssignType(pluginTypes,it.extension)
                }
                else if(fontTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(fontTypes,it.extension)
                    fontList.add(it.path)
                }
                else if(videoTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(videoTypes,it.extension)
                }
                else if(scriptTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(scriptTypes,it.extension)
                    scriptList.add(it.path)
                }
                else if(vfxTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(vfxTypes,it.extension)
                    vfxList.add(it.path)
                }
                else if(shaderTypes.types.contains(it.extension.toLowerCase()))
                {
                    AssignType(shaderTypes,it.extension)
                    shaderList.add(it.path)
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

        assetInfo += "Folders in Assets Directory: " + directoryCount + '\n'


        assetInfo += "\n     Image Files In Project:\n"
        AddToResultsString(imageTypes)


        assetInfo += "\nAudio Files in Project:\n"
        AddToResultsString(audioTypes)


        assetInfo += "\nModel Files in Project:\n"
        AddToResultsString(modelTypes)


        assetInfo += "\nUnity Native Files in Project:\n"
        AddToResultsString(nativeTypes)


        assetInfo += "\nText Files in Project:\n"
        AddToResultsString(textTypes)


        assetInfo += "\nPrefab Files in Project:\n"
        AddToResultsString(prefabType)


        assetInfo += "\nPlugin Files in Project:\n"
        AddToResultsString(pluginTypes)


        assetInfo += "\nFont Files in Project:\n"
        AddToResultsString(fontTypes)


        assetInfo += "\nVideo Files in Project:\n"
        AddToResultsString(videoTypes)

        assetInfo += "\nScript Files in Project:\n"
        AddToResultsString(scriptTypes)


        assetInfo += "\nVisual Effect Files in Project:\n"
        AddToResultsString(vfxTypes)


        assetInfo += "\nShader Files in Project:\n"
        AddToResultsString(shaderTypes)

        assetInfo += "\nOther File types in Project:\n"
        AddToResultsString(otherTypes)


    }
    fun AddToResultsString(currentType: AssetType)
    {
        var amountPrinted = 0;

        if(currentType.typeTotal == 0)
            assetInfo += "     None"

        for(i in 0..currentType.types.count()-1)
        {
            if(currentType.counts[i] == 0)
                continue

            assetInfo += "     " + currentType.types[i] + ": " + currentType.counts[i] + "   "
            amountPrinted++

            if(amountPrinted == GetTotalDifferentTypes(currentType))
                continue

            if(amountPrinted > 5 )
            {
                assetInfo += '\n'
                amountPrinted = 0
            }
        }
        assetInfo += '\n'
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

    fun GetTotalDifferentTypes(currentType: AssetType):Int
    {
        var total:Int = 0

        for(i in 0..currentType.counts.count()-1)
        {
            if( currentType.counts[i] > 0)
                total++
        }

        return total
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
