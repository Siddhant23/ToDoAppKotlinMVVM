import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eko.sidtestandroid.model.data.TODO
import com.eko.sidtestandroid.model.local.AppDatabase
import com.eko.sidtestandroid.model.local.TodoDAO
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TodoTest {
    private lateinit var todoDao: TodoDAO
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        todoDao = db.todoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeItemAndReadInList() {
        val dummyList  = ArrayList<TODO>()
        val todoItem = TODO("2", 23, "Sid", true)
        dummyList.add(todoItem)
        todoDao.insertAll(dummyList)

        val oldList = todoDao.getAll()
        Assert.assertArrayEquals(dummyList.toTypedArray(), oldList.toTypedArray())
    }
}