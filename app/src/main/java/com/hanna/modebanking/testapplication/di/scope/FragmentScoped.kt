package com.hanna.modebanking.testapplication.di.scope

import javax.inject.Scope

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * [com.hanna.modebanking.testapplication.di.components.NetComponent] is a scoped component (`@Singleton`, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@MustBeDocumented
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScoped