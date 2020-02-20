package com.todo.list

import com.todo.list.controllers.TaskController // Update WelcomeController to TaskController
import dev.alpas.routing.RouteGroup
import dev.alpas.routing.Router

// https://alpas.dev/docs/routing
fun Router.addRoutes() = apply {
    group {
        webRoutesGroup()
    }.middlewareGroup("web")

    apiRoutes()
}

private fun RouteGroup.webRoutesGroup() {
    get("/", TaskController::index).name("welcome") // Update WelcomeController to TaskController

    // Add the following routes
    post("/", TaskController::class).name("store")
    delete("/", TaskController::class).name("delete")
    patch("/", TaskController::class).name("update")
}

private fun Router.apiRoutes() {
    // register API routes here
}
