package com.maitre.menuservice.exception

class OptionalNotFoundException(id: String) : RuntimeException("Optional not found. id=$id")