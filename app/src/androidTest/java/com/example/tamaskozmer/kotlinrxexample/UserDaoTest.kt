package com.example.tamaskozmer.kotlinrxexample

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.persistence.AppDatabase
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Tamas_Kozmer on 7/24/2017.
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    lateinit var userDao: UserDao
    lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndGet() {
        val users = listOf(User(1, "Name", 100, "url"), User())
        userDao.insertAll(users)

        val allUsers = userDao.getAllUsers()
        Assert.assertEquals(users, allUsers)
    }

    @Test
    fun testUsersOrderedByCorrectly() {
        val users = listOf(
                User(1, "Name", 100, "url"),
                User(2, "Name2", 500, "url"),
                User(3, "Name3", 300, "url"))
        userDao.insertAll(users)

        val allUsers = userDao.getAllUsers()
        val expectedUsers = users.sortedByDescending { it.reputation }
        Assert.assertEquals(expectedUsers, allUsers)
    }

    @Test
    fun testConflictingInserts() {
        val users = listOf(
                User(1, "Name", 100, "url"),
                User(2, "Name2", 500, "url"),
                User(3, "Name3", 300, "url"))

        val users2 = listOf(
                User(1, "Name", 1000, "url"),
                User(2, "Name2", 700, "url"),
                User(4, "Name3", 5500, "url"))
        userDao.insertAll(users)
        userDao.insertAll(users2)

        val allUsers = userDao.getAllUsers()
        val expectedUsers = listOf(
                User(4, "Name3", 5500, "url"),
                User(1, "Name", 1000, "url"),
                User(2, "Name2", 700, "url"),
                User(3, "Name3", 300, "url"))

        Assert.assertEquals(expectedUsers, allUsers)
    }
}