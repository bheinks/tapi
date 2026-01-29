package dev.heinkel.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.jooq.generated.tabby.tables.pojos.User
import org.jooq.generated.tabby.tables.User.USER

class UserRepository(val dslContext: DSLContext) {
    suspend fun allUsers(): List<User> =
        withContext(Dispatchers.IO) {
            dslContext.selectFrom(USER).fetchInto(User::class.java)
        }
}