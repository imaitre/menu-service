package com.maitre.menuservice.exception

class ProductNotFoundException(id: String) : RuntimeException("No product found. id=$id")
