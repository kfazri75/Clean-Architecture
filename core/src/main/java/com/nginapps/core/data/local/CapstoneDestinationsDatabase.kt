package com.nginapps.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nginapps.core.data.local.dao.DestinationDao
import com.nginapps.core.model.Destination
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [Destination::class],
    version = DatabaseMigrations.DB_VERSION
)
abstract class CapstoneDestinationsDatabase : RoomDatabase() {

    abstract fun getDestinationsDao(): DestinationDao

    companion object {
        private const val DB_NAME = "capstone_database.db"

        @Volatile
        private var INSTANCE: CapstoneDestinationsDatabase? = null

        fun getInstance(context: Context): CapstoneDestinationsDatabase {
            val parser: ByteArray = SQLiteDatabase.getBytes(DB_NAME.toCharArray())
            val factory = SupportFactory(parser)
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CapstoneDestinationsDatabase::class.java,
                    DB_NAME
                )
                    .addMigrations(*DatabaseMigrations.MIGRATIONS)
                    .openHelperFactory(factory)
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}