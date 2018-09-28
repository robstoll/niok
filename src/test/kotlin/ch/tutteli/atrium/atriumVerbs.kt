package ch.tutteli.atrium

import ch.tutteli.atrium.AssertionVerb.ASSERT
import ch.tutteli.atrium.AssertionVerb.EXPECT_THROWN
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.reporting.reporter
import ch.tutteli.atrium.reporting.translating.StringBasedTranslatable

internal fun <T : Any> assert(subject: T) =
    AssertImpl.coreFactory.newReportingPlant(ASSERT, { subject }, reporter)

internal fun <T : Any> assert(subject: T, assertionCreator: Assert<T>.() -> Unit) =
    AssertImpl.coreFactory.newReportingPlantAndAddAssertionsCreatedBy(ASSERT, { subject }, reporter, assertionCreator)

internal fun <T : Any?> assert(subject: T) =
    AssertImpl.coreFactory.newReportingPlantNullable(ASSERT, { subject }, reporter)

internal fun expect(act: () -> Unit) = AssertImpl.throwable.thrownBuilder(EXPECT_THROWN, act, reporter)


internal enum class AssertionVerb(override val value: String) : StringBasedTranslatable {
    ASSERT("assert"),
    EXPECT_THROWN("expect the thrown exception")
}
