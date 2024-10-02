package com.example.restHateoas.architectureTest

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import kotlin.test.Test


class CleanArchitectureTest {
    @Test
    fun domainLayerRules() {
        val importedClasses = ClassFileImporter().importPackages("com.example.restHateoas")

        val ruleDomainLayerAvoidDependency: ArchRule =
            noClasses().that().resideInAPackage("..domainLayer..")
                .should().dependOnClassesThat().resideInAPackage("..businessLayer..")
                .orShould().dependOnClassesThat().resideInAPackage("..interfaceAdaptersLayer..")

        ruleDomainLayerAvoidDependency.check(importedClasses)

        val ruleDomainLayerPublic: ArchRule = classes()
            .that().resideInAPackage("..domainLayer..")
            .should().onlyBeAccessed()
            .byAnyPackage("..domainLayer..", "..businessLayer..", "..interfaceAdaptersLayer..")

        ruleDomainLayerPublic.check(importedClasses)

    }

    @Test
    fun businessLayerRules() {
        val importedClasses = ClassFileImporter().importPackages("com.example.restHateoas")

        val ruleBusinessLayerAvoidDependency: ArchRule =
            noClasses().that().resideInAPackage("..businessLayer..")
                .should().dependOnClassesThat().resideInAPackage("..interfaceAdaptersLayer..")

        ruleBusinessLayerAvoidDependency.check(importedClasses)

        val ruleBusinessLayerPublic: ArchRule = classes()
            .that().resideInAPackage("..businessLayer..")
            .should().onlyBeAccessed().byAnyPackage("..businessLayer..", "..interfaceAdaptersLayer..")
            .orShould().onlyBeAccessed().byClassesThat().areTopLevelClasses()

        ruleBusinessLayerPublic.check(importedClasses)
    }

    @Test
    fun interfaceAdaptersLayerRules() {
        val importedClasses = ClassFileImporter().importPackages("com.example.restHateoas")

        val ruleInterfaceAdaptersLayerPublic: ArchRule = classes()
            .that().resideInAPackage("..interfaceAdaptersLayer..")
            .should().onlyBeAccessed().byAnyPackage("..interfaceAdaptersLayer..")
            .orShould().onlyBeAccessed().byClassesThat().areTopLevelClasses()

        ruleInterfaceAdaptersLayerPublic.check(importedClasses)
    }

    @Test
    fun everythingPassThroughBusinessLayer() {
        val importedClasses = ClassFileImporter().importPackages("com.example.restHateoas")

        val ruleEverythingPassThroughBusinessLayerPersistence: ArchRule = classes()
            .that().resideInAPackage("..interfaceAdaptersLayer.persistence..")
            .should().dependOnClassesThat().resideInAPackage("..businessLayer..")
            .orShould().dependOnClassesThat().resideOutsideOfPackages("com.example.restHateoas")

        ruleEverythingPassThroughBusinessLayerPersistence.check(importedClasses)

        val ruleEverythingPassThroughBusinessLayerController: ArchRule = classes()
            .that().resideInAPackage("..interfaceAdaptersLayer.controllers..")
            .should().dependOnClassesThat().resideInAPackage("..businessLayer..")
            .orShould().dependOnClassesThat().resideOutsideOfPackages("com.example.restHateoas")

        ruleEverythingPassThroughBusinessLayerController.check(importedClasses)
    }
}