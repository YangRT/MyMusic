package com.example.mymusic.base.model

data class Artist(var name: String,
                  var id: Long,
                  var picId: Long,
                  var img1v1Id: Long,
                  var briefDesc: String,
                  var picUrl: String,
                  var img1v1Url: String,
                  var albumSize: Int,
                  var alias: List<String>,
                  var trans: String,
                  var musicSize: Int,
                  var topicPerson: Int)

data class Album(
    var name: String,
    var id: Long,
    var type: String,
    var size: Int,
    var picId: Int,
    var blurPicUrl: String,
    var companyId: Int,
    var pic: Int,
    var picUrl: String,
    var publishTime: Long,
    var description: String,
    var tags: String,
    var company: String,
    var briefDesc: String,
    var artist: Artist,
    var songs: List<String>,
    var alias: List<String>,
    var status: Int,
    var copyrightId: Int,
    var commentThreadId: String,
    var artists: List<Artist>,
    var subType: String,
    var transName: String,
    var mark: Int,
    var picId_str: String)

data class WMusic(
    var name: String,
    var id: Long,
    var size: Int,
    var extension: String,
    var sr: Int,
    var dfsId: Int,
    var bitrate: Int,
    var playTime: Long,
    var volumeDelta: Int)


data class FreeTrialPrivilege(
    var resConsumable: Boolean,
    var userConsumable: Boolean)

data class ChargeInfoList(
    var rate: Int,
    var chargeUrl: String,
    var chargeMessage: String,
    var chargeType: Int)

data class Privilege(var id: Long,
                     var fee: Int,
                     var payed: Int,
                     var st: Int,
                     var pl: Int,
                     var dl: Int,
                     var sp: Int,
                     var cp: Int,
                     var subp: Int,
                     var cs: Boolean,
                     var maxbr: Int,
                     var fl: Int,
                     var toast: Boolean,
                     var flag: Int,
                     var preSell: Boolean,
                     var playMaxbr: Int,
                     var downloadMaxbr: Int,
                     var freeTrialPrivilege: FreeTrialPrivilege,
                     var chargeInfoList: List<ChargeInfoList>)

data class Song(var name: String,
                var id: Long,
                var position: Int,
                var alias: List<String>? = null,
                var status: Int,
                var fee: Int,
                var copyrightId: Int,
                var disc: String? = null,
                var no: Int,
                var artists: List<Artist>? = null,
                var album: Album? = null,
                var starred: Int,
                var popularity: Int,
                var score: Int,
                var starredNum: Int,
                var duration: Int,
                var playedNum: Int,
                var dayPlays: Int,
                var hearTime: Int,
                var ringtone: String,
                var crbt: String,
                var audition: String,
                var copyFrom: String,
                var commentThreadId: String,
                var rtUrl: String,
                var ftype: Int,
                var rtUrls: List<String>,
                var copyright: Int,
                var transName: String,
                var sign: String,
                var mark: Int,
                var noCopyrightRcmd: String,
                var rtype: Int,
                var rurl: String,
                var mvid: Int,
                var hMusic: WMusic,
                var mMusic: WMusic,
                var lMusic: WMusic,
                var bMusic: WMusic,
                var mp3Url: String,
                var exclusive: Boolean,
                var privilege: Privilege)


data class Ar(val id: Long, val name: String, val alia: List<String>)

data class AI(val id: Long, val name: String, val picUrl: String)

data class HotSong(val rtUrls: List<String>, val noCopyrightRcmd: String, val no: Int, val fee: Int, val alia: List<String>, val name: String, val privilege: Privilege, val id: Long, val ar: List<Ar>, val al: AI)



