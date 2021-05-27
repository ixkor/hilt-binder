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

package com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.javac.model.ContributionType
import com.paulrybitskyi.hiltbinder.processor.javac.model.MAP_KEY_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.javac.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getAnnoMarkedWithSpecificAnno
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getBindAnnotation
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getContributesToArg
import com.paulrybitskyi.hiltbinder.processor.javac.utils.getType
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class ContributionTypeDetector(
    private val elementUtils: Elements,
    private val typeUtils: Types,
    private val messageProvider: MessageProvider
) {


    fun detectType(annotatedElement: TypeElement): ContributionType? {
        val collection = annotatedElement.getBindAnnotation(elementUtils, typeUtils).getContributesToArg()

        return when(collection) {
            BindType.Collection.NONE -> null
            BindType.Collection.SET -> ContributionType.Set
            BindType.Collection.MAP -> annotatedElement.createMapContributionType()
        }
    }


    private fun TypeElement.createMapContributionType(): ContributionType {
        val daggerMapKeyType = elementUtils.getType(MAP_KEY_TYPE_CANON_NAME)
        val mapKeyAnnotation = typeUtils.getAnnoMarkedWithSpecificAnno(this, daggerMapKeyType)
            ?: throw HiltBinderException(messageProvider.noMapKeyError(), this)

        return ContributionType.Map(mapKeyAnnotation)
    }


}