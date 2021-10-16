package com.example.model

import java.io.File

class AssetsExtractor(projectDirectory: File?) {

    val assetDirectory = projectDirectory?.path + "/Assets"

    lateinit var assetInfo:String

    var  directoryCount:Int = 0



    val imageTypes = AssetType("Images", listOf("png","bmp","tif","tiff","tga","gif","jpg","jpeg","psd","iff","pict","pic","pct","exr","hdr"))
    val audioTypes = AssetType("Audio", listOf("ogg", "aif", "aiff", "flac", "wav", "mp3", "mod", "it", "s3m","xm"))
    val modelTypes = AssetType("Models", listOf("fbx", "mb", "ma", "max", "jas", "dae", "dxf", "obj", "c4d", "blend", "lxo"))
    val nativeTypes = AssetType("Unity Native", listOf("anim", "animset", "asset", "blendtree", "buildreport", "colors", "controller", "cubemap"
        , "curves", "curvesNormalized", "flare", "fontsettings", "giparams", "gradients", "guiskin", "ht", "mask", "mat", "mesh"
        , "mixer", "overrideController", "particleCurves", "particleCurvesSigned", "particleDoubleCurves", "particleDoubleCurvesSigned", "physicMaterial", "physicsMaterial2D"
        , "playable", "preset", "renderTexture", "shadervariants", "spriteatlas", "state", "statemachine", "texture2D", "transition", "webCamTexture", "brush", "terrainlayer"
        , "signal"))
    val textTypes = AssetType("Text", listOf("txt", "html", "htm", "xml", "json", "csv", "yaml", "bytes", "fnt", "manifest", "md", "js", "boo", "rsp"))

    val prefabType = AssetType("Prefabs", listOf("prefab"))

    //plugin
    //font
    //video
    //script

    fun ScanAssetFolder()
    {




        File(assetDirectory).walkTopDown().forEach {

            if(!it.isDirectory) {

                if(imageTypes.types.contains(it.extension))
                {
                    val index = imageTypes.types.indexOf(it.extension)
                    imageTypes.counts[index]++
                    imageTypes.typeTotal++
                }
                else if(audioTypes.types.contains((it.extension)))
                {
                    val index = audioTypes.types.indexOf(it.extension)
                    audioTypes.counts[index]++
                    audioTypes.typeTotal++;
                }
                else if(modelTypes.types.contains((it.extension)))
                {
                    val index = modelTypes.types.indexOf((it.extension))
                    modelTypes.counts[index]++
                    modelTypes.typeTotal++
                }
                else if(nativeTypes.types.contains((it.extension)))
                {
                    val index = nativeTypes.types.indexOf((it.extension))
                    nativeTypes.counts[index]++
                    nativeTypes.typeTotal++
                }
                else if(textTypes.types.contains((it.extension)))
                {
                    val index = textTypes.types.indexOf((it.extension))
                    textTypes.counts[index]++
                    textTypes.typeTotal++
                }
                else if(prefabType.types.contains((it.extension)))
                {
                    val index = prefabType.types.indexOf((it.extension))
                    prefabType.counts[index]++
                    prefabType.typeTotal++
                }
               // println(it.name)


            }
            else
            {
                directoryCount++

            }
        }

        println("Total Asset Directories: " + directoryCount)

        println("Audio Types Found: " + audioTypes.typeTotal)




        println("Image Types Found: " + imageTypes.typeTotal)

        for(i in 0..imageTypes.types.count()-1)
        {
            println(imageTypes.types[i] + ": " + imageTypes.counts[i] )
        }
        println("Model Types Found: " + modelTypes.typeTotal)
        for(i in 0..modelTypes.types.count()-1)
        {
            println(modelTypes.types[i] + ": " + modelTypes.counts[i] )
        }
       println("Unity Native Types Found: " + nativeTypes.typeTotal)
        for(i in 0..nativeTypes.types.count()-1)
        {
            println(nativeTypes.types[i] + ": " + nativeTypes.counts[i] )
        }
        println("Text Types Found: " + textTypes.typeTotal)
        for(i in 0..textTypes.types.count()-1)
        {
            println(textTypes.types[i] + ": " + textTypes.counts[i] )
        }

        for(i in 0..prefabType.types.count()-1)
        {
            println(prefabType.types[i] + ": " + prefabType.counts[i] )
        }
    }


    class AssetType(val name:String, var types:List<String> )
    {
        var counts = IntArray(types.count()) {_->0}

        var typeTotal:Int = 0;

    }
}
//image types
//jpg, jpeg, tif, tiff, tga, gif, png, psd, bmp, iff, pict, pic, pct, exr, hdr

//Audio types
//ogg, aif, aiff, flac, wav, mp3, mod, it, s3m, xm