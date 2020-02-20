package com.todo.list.controllers

import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

// add the following imports
import com.todo.list.entities.Tasks // This calls the Tasks entity created in step 4
import dev.alpas.orAbort
import me.liuwj.ktorm.dsl.delete
import me.liuwj.ktorm.dsl.update
import dev.alpas.ozone.create
import dev.alpas.ozone.latest
import dev.alpas.validation.min
import dev.alpas.validation.required
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.toList

class TaskController : Controller() {
    fun index(call: HttpCall) {

        // Go ahead and remove the following line
        // call.reply("Hello, TaskController!")

        // add in the following
        val tasks = Tasks.latest().toList()  // calls the database for all todo items
        val total = tasks.size // referring to the previous call, this checks to see how many todos are in the database
        val completed = tasks.count { it.completed } // now, we see how many of the total todos have been completed

        // Now we will call the front end page template to display to the user, passing along the values we defined above
        call.render ("welcome", mapOf("tasks" to tasks, "total" to total, "completed" to completed))
    }

    // Let's create a function to store new todo items that have been added via the front end
    fun store(call: HttpCall) {

        // Before we write the new todo task to the database, let's first validate to make sure there is data with at least 2 characters
        call.applyRules("newTask") {
            required()
            min(2)
        }.validate() // If validation fails, a message will be sent back to front end with the failure reasons

        // If validation has passed, the next part will write new data to database
        Tasks.create() {
            it.name to call.stringParam("newTask") // this pulls the todo text from the http call
        }

        // If new todo task has successfully been written to db, a success message is sent back to front end
        flash("success", "Successfully added to-do")
        call.redirect().back()
    }

    // Next up, let's create a function to delete a todo task
    fun delete(call: HttpCall) {
        // We will get the todo tasks id that is marked for deletion and then remove todo from database
        val id = call.longParam("id").orAbort()
        Tasks.delete { it.id eq id }
        flash("success", "Successfully removed to-do")
        call.redirect().back()
    }

    // Lastly, let's create a function to update a todo task as being completed; or, to reverse completion state
    fun update(call: HttpCall) {
        val id = call.longParam("id").orAbort()

        // Let's get the current boolean state of todo task and then change state
        val markAsComplete = call.param("state") != null

        Tasks.update {
            it.completed to markAsComplete
            where {
                it.id eq id
            }
        }

        // based on if markAsComplete is equal to 'True', let's flash the appropriate message
        if (markAsComplete) {
            flash("success", "Successfully completed the to-do")
        } else {
            flash("success", "Successfully updated the to-do")
        }

        call.redirect().back()
    }
}

// For more information on Controllers, visit https://alpas.dev/docs/controllers#main