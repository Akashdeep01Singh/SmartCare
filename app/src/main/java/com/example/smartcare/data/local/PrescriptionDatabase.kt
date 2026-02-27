package com.smartcare.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Prescription::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PrescriptionDatabase : RoomDatabase() {

    abstract fun prescriptionDao(): PrescriptionDao

    companion object {
        @Volatile
        private var INSTANCE: PrescriptionDatabase? = null

        fun getDatabase(context: Context): PrescriptionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrescriptionDatabase::class.java,
                    "prescriptions_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
