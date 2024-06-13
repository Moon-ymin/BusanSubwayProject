import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "station_points.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "stations"
        const val COLUMN_TITLE = "scode"
        const val COLUMN_X1 = "x1"
        const val COLUMN_Y1 = "y1"
        const val COLUMN_X2 = "x2"
        const val COLUMN_Y2 = "y2"
    }

    // 1. DB 처음에 생성될 때 호출
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_TITLE TEXT PRIMARY KEY, " +
                "$COLUMN_X1 INTEGER, " +
                "$COLUMN_Y1 INTEGER, " +
                "$COLUMN_X2 INTEGER, " +
                "$COLUMN_Y2 INTEGER)"
        db?.execSQL(createTable)
    }

    // 2. DB 버전이 업그레이드될 때 호출되며, 기존 테이블을 삭제하고 다시 생성
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 3. 데이터 삽입 시 중복된 scode 체크 후 삽입
    fun insertOrIgnore(scode: String, x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
        val db = writableDatabase

        // 이미 존재하는지 확인
        if (!isRecordExists(db, scode)) {
            val contentValues = ContentValues().apply {
                put(COLUMN_TITLE, scode)
                put(COLUMN_X1, x1.toInt())
                put(COLUMN_Y1, y1.toInt())
                put(COLUMN_X2, x2.toInt())
                put(COLUMN_Y2, y2.toInt())
            }

            db.insert(TABLE_NAME, null, contentValues)
            return true
        }
        return false
    }

    // 데이터베이스에 scode가 이미 존재하는지 확인
    private fun isRecordExists(db: SQLiteDatabase, scode: String): Boolean {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(scode))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}
