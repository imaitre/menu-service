package com.maitre.menuservice.exception

class MissingParameterException(parameterName: String) : RuntimeException("The $parameterName query parameter is missing.") {

}
