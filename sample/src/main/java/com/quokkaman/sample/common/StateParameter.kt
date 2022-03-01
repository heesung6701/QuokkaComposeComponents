package com.quokkaman.sample.common

interface StateParameter<T> {
    val name: String
    val initValue: T
    fun onValueChange(newValue: T)
}

abstract class IntParameter(override val name: String, override val initValue: Int) : StateParameter<Int>

abstract class FloatParameter(override val name: String, override val initValue: Float) : StateParameter<Float>

abstract class SeekBarParameter(override val name: String, override val initValue: Float, val minValue: Float, val maxValue: Float, val stepSize: Float) : FloatParameter(name, initValue)

abstract class BooleanParameter(override val name: String, override val initValue: Boolean) : StateParameter<Boolean>

abstract class StringParameter(override val name: String, override val initValue: String): StateParameter<String>