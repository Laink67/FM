package com.example.fm.data

class BaseRepository {
    private var dataManager: DataManager? = null

    var _dataManager: DataManager?
        get() {
            if (dataManager == null)
                dataManager = DataManager()
            return dataManager
        }
        private set(value) {
            dataManager = value
        }

}