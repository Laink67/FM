package com.example.fm.data

import org.jetbrains.exposed.sql.Database

open class DataManager {
    private var instance: DataManager? = null

    var db = Database.connect(
        "jdbc:sqlserver://31.31.196.61;databaseName=u0588704_potapovdb",
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        user = "u0588704_potapov",
        password = "qwerty123!"
    )


    fun getInstance(): DataManager? {
        if (instance == null)
            instance = DataManager()

        return instance
    }
}