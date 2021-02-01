package com.maitre.menuservice.exception

class MenuNotFoundException(id: String) : RuntimeException("No menu found. id=$id")