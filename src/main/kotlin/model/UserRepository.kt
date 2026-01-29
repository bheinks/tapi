package dev.heinkel.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.jooq.generated.tabby.tables.pojos.User
import org.jooq.generated.tabby.tables.User.USER

class UserRepository(val dslContext: DSLContext) {
    suspend fun getUsers(): List<User> =
        withContext(Dispatchers.IO) {
            dslContext.selectFrom(USER)
                .fetchInto(User::class.java)
        }

    suspend fun getUserByUsername(username: String): User? =
        withContext(Dispatchers.IO) {
            dslContext.selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOneInto(User::class.java)
        }
}