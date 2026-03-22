package com.takethistoyourgrave.todos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TodoEntity::class], version = 3)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE todos ADD COLUMN color INTEGER NOT NULL DEFAULT ${android.graphics.Color.WHITE}")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE todos ADD COLUMN created_at INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE todos ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun create(context: Context): TodoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todos.db"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
