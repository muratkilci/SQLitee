package com.example.sqlitee

class User {
    var id: Int = 0
    var namesurname: String = ""
    var age: Int = 0

    constructor(namesurname: String, age: Int) {
        this.namesurname = namesurname
        this.age = age
    }

    constructor() {

    }
}