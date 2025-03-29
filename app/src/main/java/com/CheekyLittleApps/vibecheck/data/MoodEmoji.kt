package com.CheekyLittleApps.vibecheck.data

enum class MoodEmoji(val emoji: String, val numScale: Int) {
    DISAPPOINTED_FACE("\uD83D\uDE1E", 1),
    SLIGHTLY_FROWNING_FACE("\uD83D\uDE41", 2),
    NEUTRAL_FACE("\uD83D\uDE10", 3),
    SLIGHTLY_SMILING_FACE("\uD83D\uDE42", 4),
    GRINNING_FACE("\uD83D\uDE00", 5),

//    SAD("😀"),
//    SURPRISED("😀"),
//    FRUSTRATED(0X000000),
//    STRESSED(0X000000),
//    ANXIOUS(0X000000),
//    AWKWARD(0X000000),
//    BORED(0X000000),
//    CONFUSED(0X000000),
//    CONTENT(0X000000),
//    DISAPPOINTED(0X000000),
//    DISGUSTED(0X000000),
//    DISTRUSTFUL(0X000000),
//    EXCITED(0X000000),
//    AFRAID(0X000000),
//    GRATEFUL(0X000000),
//    GUILTY(0X000000),
}

fun unicodeToString(unicode: Int){
    String(Character.toChars(unicode))
}