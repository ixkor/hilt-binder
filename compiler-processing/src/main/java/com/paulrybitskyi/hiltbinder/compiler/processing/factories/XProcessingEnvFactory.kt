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

package com.paulrybitskyi.hiltbinder.compiler.processing.factories

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.JavacProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.KspProcessingEnv
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment

object XProcessingEnvFactory {


    fun createJavacEnv(
        processingEnv: ProcessingEnvironment,
        roundEnv: RoundEnvironment
    ): XProcessingEnv {
        return JavacProcessingEnv(processingEnv, roundEnv)
    }


    fun createKspEnv(
        resolver: Resolver,
        codeGenerator: CodeGenerator,
        kspLogger: KSPLogger
    ): XProcessingEnv {
        return KspProcessingEnv(
            resolver = resolver,
            codeGenerator = codeGenerator,
            kspLogger = kspLogger
        )
    }


}