package com.example.olgadominguez.todoachieved.database;


import android.app.Application;

import com.example.olgadominguez.todoachieved.task.model.DaoMaster;
import com.example.olgadominguez.todoachieved.task.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DatabaseHelper {
    private static DaoSession daoSession;

    public synchronized static DaoSession getDaoSession(Application application) {
        if (daoSession != null) {
            return daoSession;
        }
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, "todoachieved-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        return daoSession;
    }
}
