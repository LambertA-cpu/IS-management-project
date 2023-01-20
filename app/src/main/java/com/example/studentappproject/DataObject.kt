package com.example.studentappproject

object DataObject {
    var listdata = mutableListOf<CardInfo>()

    fun setData(title: String, priority: String, details: String) {
        listdata.add(CardInfo(title, priority, details))
    }

    fun getAllData(): List<CardInfo> {
        return listdata
    }

    fun deleteAll(){
        listdata.clear()
    }

    fun getData(pos:Int): CardInfo {
        return listdata[pos]
    }

    fun deleteData(pos:Int){
        listdata.removeAt(pos)
    }

    fun updateData(pos:Int,title:String,priority:String, details: String)
    {
        listdata[pos].title=title
        listdata[pos].priority=priority
        listdata[pos].details=details
    }

}