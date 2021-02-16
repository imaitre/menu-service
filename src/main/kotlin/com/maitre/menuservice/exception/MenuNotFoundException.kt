package com.maitre.menuservice.exception

class MenuNotFoundException(id: String) : RuntimeException("Menu not found. id=$id")