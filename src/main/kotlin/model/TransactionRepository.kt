package dev.heinkel.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.jooq.generated.tabby.tables.pojos.Transaction
import org.jooq.generated.tabby.tables.Transaction.TRANSACTION

class TransactionRepository(val dslContext: DSLContext) {
    suspend fun getTransactions(): List<Transaction> =
        withContext(Dispatchers.IO) {
            dslContext.selectFrom(TRANSACTION).fetchInto(Transaction::class.java)
        }
}