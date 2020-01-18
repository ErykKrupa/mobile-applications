package com.example.simplemath

import retrofit2.Call
import kotlin.reflect.KCallable

enum class Operation(val pretyName: String, val extra: Boolean = false, val range: Boolean = false, val extraName: String? = null) {
    SIMPLIFY(  "Simplify"),
    FACTOR(  "Factor"),
    DERIVE(  "Derive"),
    INTEGRATE(  "Integrate"),
    ZEROES(  "Find 0's"),
    TANGENT(  "Find Tangent", extra = true, extraName = "Tangent to"),
    AREA(  "Area Under Curve", range = true),
    COS(  "Cosine"),
    SIN(  "Sine"),
    TAN(  "Tangent"),
    ARCCOS(  "Inverse Cosine"),
    ARCSIN(  "Inverse Sine"),
    ARCTAN(  "Inverse Tangent"),
    ABS(  "Absolute Value"),
    LOG(  "Logarithm", extra = true, extraName = "Base");


    override fun toString(): String {
        return pretyName
    }
    fun type(): Int{
        var a = 0
        if (extra) a+=1
        if (range) a+=2
        return a
    }

    fun calc(newton: NewtonAPI, data: String): Call<NewtonResult>{
        return (NewtonAPI::class.members.find {
                a->a.name.equals(name, true)
        } as KCallable<Call<NewtonResult>>)
            .call(newton, data)
    }

}