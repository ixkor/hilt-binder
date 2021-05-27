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

package com.paulrybitskyi.hiltbinder.processor.javac.generator

import com.paulrybitskyi.hiltbinder.common.utils.PACKAGE_SEPARATOR
import com.paulrybitskyi.hiltbinder.processor.javac.model.ModuleSchema
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.Filer

internal class ModuleFileGenerator(
    private val typeSpecFactory: TypeSpecFactory,
    private val filer: Filer
) {


    fun generateFiles(moduleSchemas: List<ModuleSchema>) {
        for(moduleSchema in moduleSchemas) {
            val typeSpec = typeSpecFactory.createTypeSpec(moduleSchema)
            val javaFile = typeSpec.generateJavaFile(moduleSchema.packageName)
            val filerSourceFile = filer.createSourceFile(
                moduleSchema.createFileName(),
                *typeSpec.originatingElements.toTypedArray()
            )

            filerSourceFile.openWriter().use { writer ->
                val javaCode = javaFile.toString()
                    .addNewlineCharacterAfterComment()
                    .removeTrailingNewline()

                writer.write(javaCode)
            }
        }
    }


    private fun TypeSpec.generateJavaFile(packageName: String): JavaFile {
        return JavaFile.builder(packageName, this)
            .addFileComment("Generated by @BindType. Do not modify!")
            .build()
    }


    private fun ModuleSchema.createFileName(): String {
        return when {
            packageName.isEmpty() -> interfaceName
            else -> "$packageName$PACKAGE_SEPARATOR$interfaceName"
        }
    }


    private fun String.addNewlineCharacterAfterComment(): String {
        return replaceFirst("\n", "\n\n")
    }


    private fun String.removeTrailingNewline(): String {
        return trim()
    }


}