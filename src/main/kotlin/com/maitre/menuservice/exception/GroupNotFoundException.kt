package com.maitre.menuservice.exception

class GroupNotFoundException(id: String) : RuntimeException("Group not found. $id")