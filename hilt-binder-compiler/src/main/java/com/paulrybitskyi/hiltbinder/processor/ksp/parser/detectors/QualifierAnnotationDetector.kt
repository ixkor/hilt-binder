/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getAnnoMarkedWithSpecificAnno
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getType
import com.paulrybitskyi.hiltbinder.processor.ksp.model.QUALIFIER_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.MessageProvider
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class QualifierAnnotationDetector(
    private val elementUtils: Elements,
    private val typeUtils: Types,
    private val messageProvider: MessageProvider
) {


    fun detectAnnotation(annotatedElement: TypeElement): AnnotationMirror? {
        if(!annotatedElement.getAnnotation(BindType::class.java).withQualifier) return null

        val qualifierType = elementUtils.getType(QUALIFIER_TYPE_CANON_NAME)

        return typeUtils.getAnnoMarkedWithSpecificAnno(annotatedElement, qualifierType)
            ?: throw HiltBinderException(messageProvider.qualifierAbsentError(), annotatedElement)
    }


}