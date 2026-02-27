package com.smartcare.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [HealthRecordEntity::class, TriageReportEntity::class], version = 1, exportSchema = false)
abstract class SmartCareDatabase : RoomDatabase() {

    abstract fun smartCareDao(): SmartCareDao

    companion object {
        @Volatile
        private var INSTANCE: SmartCareDatabase? = null

        fun getDatabase(context: Context, encryptionKey: ByteArray): SmartCareDatabase {
            return INSTANCE ?: synchronized(this) {
                // SQLCipher Factory for HIPAA compliance in Offline Mode
                val supportFactory = SupportFactory(encryptionKey)

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartCareDatabase::class.java,
                    "smartcare_encrypted.db"
                )
                    .openHelperFactory(supportFactory)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
