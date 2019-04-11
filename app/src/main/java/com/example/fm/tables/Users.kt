package com.example.fm.tables

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column

class Users : IntIdTable() {
//    private var id = integer("id").autoIncrement().primaryKey()
    private var name = varchar("name", length = 20)

//    var _id: Column<Int>
//        get() = id
//        set(value) {
//            id = value
//        }

    var _name: Column<String>
        get() = name
        set(value) {
            name = value
        }

    fun Users(){}

    fun Users(/*id:Column<Int>,*/name:Column<String>){
//        this.id = id
        this.name = name
    }
}