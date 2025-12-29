package app.rickandmorty.core.navigation.serialization

import androidx.navigation3.runtime.NavKey
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoMap
import dev.zacsweers.metro.Provides
import kotlinx.serialization.KSerializer

internal class NavKeySerializerProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
) : SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {
    val symbols = resolver.getSymbolsWithAnnotation(NavKeySerializer::class.qualifiedName!!)

    val (valid, invalid) = symbols.partition { it.validate() }

    valid.filterIsInstance<KSClassDeclaration>().forEach { clazz ->
      generateProviderInterface(clazz)
    }

    return invalid
  }

  private fun generateProviderInterface(clazz: KSClassDeclaration) {
    if (!isSupportedKind(clazz)) return

    val scopeType = getScopeType(clazz) ?: return

    val packageName = clazz.packageName.asString()
    val className = clazz.simpleName.asString()
    val navKeyClassName = clazz.toClassName()
    val interfaceName = "${className}NavKeyProvider"

    val typeSpec =
      TypeSpec.interfaceBuilder(interfaceName)
        .addModifiers(KModifier.PUBLIC)
        .addOriginatingKSFile(clazz.containingFile!!)
        .addAnnotation(
          AnnotationSpec.builder(ContributesTo::class)
            .addMember("%T::class", scopeType.toClassName())
            .build()
        )
        .addType(
          TypeSpec.companionObjectBuilder()
            .addModifiers(KModifier.PUBLIC)
            .addFunction(
              FunSpec.builder("provide${className}Serializer")
                .addModifiers(KModifier.PUBLIC)
                .addAnnotation(Provides::class)
                .addAnnotation(IntoMap::class)
                .addAnnotation(
                  AnnotationSpec.builder(NavKeySerializerKey::class.asClassName())
                    .addMember("value = %T::class", navKeyClassName)
                    .build()
                )
                .returns(
                  KSerializer::class.asClassName()
                    .parameterizedBy(WildcardTypeName.producerOf(NavKey::class))
                )
                .addStatement("return %T.serializer()", navKeyClassName)
                .build()
            )
            .build()
        )
        .build()

    val fileSpec = FileSpec.builder(packageName, interfaceName).addType(typeSpec).build()
    fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
  }

  private fun isSupportedKind(clazz: KSClassDeclaration): Boolean {
    val supportedKinds = setOf(ClassKind.CLASS, ClassKind.OBJECT, ClassKind.ENUM_CLASS)

    if (clazz.classKind !in supportedKinds) {
      logger.error(
        "@NavKeySerializer was applied to ${clazz.simpleName.asString()}, " +
          "but only classes, objects, and enums are supported.",
        clazz,
      )
      return false
    }
    return true
  }

  private fun getScopeType(clazz: KSClassDeclaration): KSType? {
    val annotation =
      clazz.annotations.firstOrNull {
        it.shortName.asString() == NavKeySerializer::class.simpleName
      }

    if (annotation == null) {
      logger.error("Couldn't find the @NavKeySerializer annotation.", clazz)
      return null
    }

    val scopeArgument = annotation.arguments.firstOrNull { it.name?.asString() == "scope" }

    if (scopeArgument == null) {
      logger.error("@NavKeySerializer must have a 'scope' parameter.", clazz)
      return null
    }

    val scopeType = scopeArgument.value as? KSType
    if (scopeType == null) {
      logger.error("The 'scope' parameter must be a valid KClass type.", clazz)
      return null
    }

    return scopeType
  }

  public class Provider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
      return NavKeySerializerProcessor(
        codeGenerator = environment.codeGenerator,
        logger = environment.logger,
      )
    }
  }
}
