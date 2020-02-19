package com.todo.list.database.migrations

import com.todo.list.entities.Users
import dev.alpas.auth.PasswordResetTokens
import dev.alpas.ozone.migration.Migration

// https://alpas.dev/docs/migrations
class CreateUsersTable : Migration() {
    override fun up() {
        createTable(Users)
        createTable(PasswordResetTokens)
    }

    override fun down() {
        dropTable(Users)
        dropTable(PasswordResetTokens)
    }
}
