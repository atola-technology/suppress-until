package org.atola.annotations

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.EXPRESSION
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.FILE
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.annotation.AnnotationTarget.TYPEALIAS
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@Target(
    CLASS, ANNOTATION_CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION,
    PROPERTY_GETTER, PROPERTY_SETTER, TYPE, EXPRESSION, FILE, TYPEALIAS
)
@Retention(SOURCE)
annotation class SuppressUntil(val version: String, vararg val names: String)