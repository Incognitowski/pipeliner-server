package io.pipeliner

import com.tngtech.archunit.base.DescribedPredicate.not
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

class ArchTest {

    private val classesToCheck = ClassFileImporter().importPath("")

    @Test
    fun `Nothing on domain should depend on transport`() {
        noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("..transport..")
            .allowEmptyShould(true)
            .check(classesToCheck)
    }

    @Test
    fun `Nothing on domain should depend on data`() {
        noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat(
                resideInAnyPackage("..data..").and(
                    not(resideInAnyPackage("..data.access.."))
                )
            )
            .allowEmptyShould(true)
            .check(classesToCheck)
    }

    @Test
    fun `Nothing on transport should depend on data`() {
        noClasses().that().resideInAPackage("..transport..")
            .should().dependOnClassesThat().resideInAPackage("..data..")
            .allowEmptyShould(true)
            .check(classesToCheck)
    }

    @Test
    fun `Nothing on transport should depend on domain`() {
        noClasses().that().resideInAPackage("..transport..")
            .should().dependOnClassesThat(
                resideInAnyPackage("..domain..").and(
                    not(resideInAnyPackage(("..domain.access..")))
                )
            )
            .allowEmptyShould(true)
            .check(classesToCheck)
    }
}
