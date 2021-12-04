package com.example

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()

        val textArea by cssclass()

        val normalText by cssclass()

        val question by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        textArea {
            fontSize = 15.px
            fontWeight = FontWeight.BOLD
        }

        normalText {
            fontWeight = FontWeight.NORMAL
        }

        question{
            fill = Color.RED
        }


    }
}