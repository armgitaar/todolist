package com.todo.list.entities

import dev.alpas.ozone.*
import me.liuwj.ktorm.schema.boolean
import java.time.Instant

interface Task : OzoneEntity<Task> {
    var id: Long
    var name: String?
    var createdAt: Instant?
    var updatedAt: Instant?

    // add interface val for completed totdos as a boolean
    val completed: Boolean

    companion object : OzoneEntity.Of<Task>()
}

object Tasks : OzoneTable<Task>("tasks") {
    val id by bigIncrements()
    val name by string("name").size(150).nullable().bindTo { it.name }
    val createdAt by createdAt()
    val updatedAt by updatedAt()

    // add object val for completed todos
    val completed by boolean("completed").default(false).bindTo { it.completed }
}

// for more information on Entities, visit https://alpas.dev/docs/entity-relationship#main