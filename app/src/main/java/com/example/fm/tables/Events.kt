package com.example.fm.tables

import org.jetbrains.exposed.dao.IntIdTable

object Events : IntIdTable() {
//    private var id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", 20)
    val location = double("location")
    val description = varchar("description", length = 200)
    val usersCount = integer("usersCount")
/*
    private var userId = integer("userId")
*/

//    var _id: Column<Int>
//        get() = id
//        set(value) {
//            id = value
//        }

/*
    var _title: Column<String>
        get() = title
        set(value) {
            title = value
        }

    var _location: Column<Double>
        get() = location
        set(value) {
            location = value
        }

    var _description: Column<String>
        get() = description
        set(value) {
            description = value
        }

    var _usersCount: Column<Int>
        get() = usersCount
        set(value) {
            usersCount = value
        }
*/

/*
    var _userId: Column<Int>
        get() = userId
        set(value) {
            userId = value
        }
*/

/*
    fun Events(){}
*/

/*
    fun Events(*/
/*id: Column<Int>,*//*
 title: Column<String>, location: Column<Double>, description: Column<String>, usersCount: Column<Int>) {
//        this.id = id
        this.title = title
        this.location = location
        this.description = description
        this.usersCount = usersCount
    }
*/
}

/*
class Event(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Event>(table = Events)

    var title by Events.title
    var location     by Events.location
    var description by Events.description
    var usersCount by Events.usersCount
}

*/
