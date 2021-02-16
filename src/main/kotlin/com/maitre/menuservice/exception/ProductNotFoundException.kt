package com.maitre.menuservice.exception

class ProductNotFoundException(id: String) : RuntimeException("Product not found. id=$id")
