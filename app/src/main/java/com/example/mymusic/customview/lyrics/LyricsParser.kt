package com.example.mymusic.customview.lyrics

// 歌词解析
object LyricsParser {

    val testLyrics = "[00:04.050]\n[00:12.570]难以忘记初次见你\n[00:16.860]一双迷人的眼睛\n[00:21.460]在我脑海里\n[00:23.960]你的身影 挥散不去\n[00:30.160]握你的双手感觉你的温柔\n[00:34.940]真的有点透不过气\n[00:39.680]你的天真 我想珍惜\n[00:43.880]看到你受委屈 我会伤心\n[00:48.180]喔\n[00:50.340]只怕我自己会爱上你\n[00:55.070]不敢让自己靠的太近\n[00:59.550]怕我没什么能够给你\n[01:04.030]爱你也需要很大的勇气\n[01:08.190]只怕我自己会爱上你\n[01:12.910]也许有天会情不自禁\n[01:17.380]想念只让自己苦了自己\n[01:21.840]爱上你是我情非得已\n[01:28.810]难以忘记初次见你\n[01:33.170]一双迷人的眼睛\n[01:37.700]在我脑海里 你的身影 挥散不去\n[01:46.360]握你的双手感觉你的温柔\n[01:51.120]真的有点透不过气\n[01:55.910]你的天真 我想珍惜\n[02:00.150]看到你受委屈 我会伤心\n[02:04.490]喔\n[02:06.540]只怕我自己会爱上你\n[02:11.240]不敢让自己靠的太近\n[02:15.750]怕我没什么能够给你\n[02:20.200]爱你也需要很大的勇气\n[02:24.570]只怕我自己会爱上你\n[02:29.230]也许有天会情不自禁\n[02:33.680]想念只让自己苦了自己\n[02:38.140]爱上你是我情非得已\n[03:04.060]什么原因 耶\n[03:07.730]我竟然又会遇见你\n[03:13.020]我真的真的不愿意\n[03:16.630]就这样陷入爱的陷阱\n[03:20.700]喔\n[03:22.910]只怕我自己会爱上你\n[03:27.570]不敢让自己靠的太近\n[03:32.040]怕我没什么能够给你\n[03:36.560]爱你也需要很大的勇气\n[03:40.740]只怕我自己会爱上你\n[03:45.460]也许有天会情不自禁\n[03:49.990]想念只让自己苦了自己\n[03:54.510]爱上你是我情非得已\n[03:58.970]爱上你是我情非得已\n[04:03.000]\n"

    // 将歌词字符串解析为每句歌词数据
    fun parseLyrics(lyric: String): ArrayList<LyricsData> {
        val list: ArrayList<LyricsData> = ArrayList()
        val lrcText: String = lyric.replace("&#58;", ":")
            .replace("&#10;", "\n")
            .replace("&#46;", ".")
            .replace("&#32;", " ")
            .replace("&#45;", "-")
            .replace("&#13;", "\r")
            .replace("&#39;", "'")
        val split = lrcText.split("\n".toRegex()).toTypedArray()
        for (i in split.indices) {
            val lrc = split[i]
            if (lrc.contains(".")) {
                val min = lrc.substring(lrc.indexOf("[") + 1, lrc.indexOf("[") + 3)
                val seconds =
                    lrc.substring(lrc.indexOf(":") + 1, lrc.indexOf(":") + 3)
                val mills = lrc.substring(lrc.indexOf(".") + 1, lrc.indexOf(".") + 3)
                val startTime =
                    java.lang.Long.valueOf(min) * 60 * 1000 + java.lang.Long.valueOf(seconds) * 1000 + java.lang.Long.valueOf(
                        mills
                    ) * 10
                var text = lrc.substring(lrc.indexOf("]") + 1)
                if (text == null || "" == text) {
                    text = "music"
                }
                val lrcBean = LyricsData()
                lrcBean.setStart(startTime)
                lrcBean.setLyrics(text)
                list.add(lrcBean)
                if (list.size > 1) {
                    list[list.size - 2].setEnd(startTime)
                }
                if (i == split.size - 1) {
                    list[list.size - 1].setEnd(startTime + 100000)
                }
            }
        }
        return list
    }
}