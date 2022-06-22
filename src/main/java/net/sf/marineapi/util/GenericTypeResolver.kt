/*
 * GenericTypeResolver.java
 * Copyright (C) 2017 Kimmo Tuukkanen
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

/**
 * Utility class for resolving the generic type of a class, mainly for
 * [net.sf.marineapi.nmea.event.AbstractSentenceListener] and
 * [net.sf.marineapi.ais.event.AbstractAISMessageListener] classes where
 * the generic type needs to be resolved at runtime to filter the incoming
 * messages.
 *
 * @author Kimmo Tuukkanen, Axel Uhl
 */
object GenericTypeResolver {
    /**
     * Attempts to resolve the generic type of given class, with the assumption
     * of single generic type parameter. However, the resolving may not always
     * succeed, often due to more advanced or mixed usage of generics and
     * inheritance. For example, the generic type information may be lost
     * at compile-time because of the Java's [
 * type erasure.](https://docs.oracle.com/javase/tutorial/java/generics/nonReifiableVarargsType.html)
     *
     * @param child The class of which parents and generic types to inspect
     * @param parent The generic class that holds the type being resolved
     * @return The generic type of `parent`
     * @throws IllegalStateException If the generic type cannot be resolved
     * at runtime.
     */
    fun resolve(child: Class<*>, parent: Class<*>): Class<*> {
        val t = resolve(child, parent, HashMap())
        check(t !is TypeVariable<*>) { "Cannot resolve generic type <T>, use constructor with Class<T> param." }
        return t as Class<*>
    }

    /**
     * Resolves the generic type of class.
     *
     * @param child The class of calling listener implementation.
     * @param parent The parent class that holds the generic type.
     * @param types Variables and types memo
     */
    private fun resolve(
        child: Class<*>, parent: Class<*>,
        types: MutableMap<TypeVariable<*>, Type>
    ): Type {
        val superClass = child.genericSuperclass
        if (superClass is ParameterizedType) {
            val rawType = superClass.rawType as Class<*>
            val typeParams = rawType.typeParameters
            val typeArgs = superClass.actualTypeArguments
            for (i in typeParams.indices) {
                if (typeArgs[i] is TypeVariable<*>) {
                    val arg = typeArgs[i] as TypeVariable<*>
                    types[typeParams[i]] = types.getOrDefault(arg, arg)
                } else {
                    types[typeParams[i]] = typeArgs[i]
                }
            }
            return if (rawType == parent) types.getOrDefault(typeParams[0], typeParams[0])
            else resolve(rawType, parent, types)
        }
        return resolve(superClass as Class<*>, parent, types)
    }
}