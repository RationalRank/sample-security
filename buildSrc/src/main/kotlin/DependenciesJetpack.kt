object VersionsJetpack {

    const val androidxCoreKtx = "1.1.0-beta01"
    const val androidxAppcompat = "1.0.2"
    const val androidxRoom = "2.1.0-alpha07"
    const val androidxLifecycle = "2.0.0"
    const val androidxLifecycleSavedState = "1.0.0-alpha02"
    const val androidxNavigation = "2.0.0"
    const val androidxWorkManager = "2.0.1"

    const val androidx = "1.0.0"
    const val androidxMaterial = "1.0.0-rc01"
    const val androidxConstraintLayout = "2.0.0-alpha5"
    const val androidxLegacySupport = "1.0.0"

    const val playServiceMaps = "15.0.1"
    const val playServices = "16.0.0"

    const val mapsUtils = "0.5+"

    const val multidex = "1.0.3"
}

object DependenciesJetpack {
    
    const val androidxCoreKtx = "androidx.core:core-ktx:${VersionsJetpack.androidxCoreKtx}"

    const val androidxAppcompatLib = "androidx.appcompat:appcompat:${VersionsJetpack.androidxAppcompat}"
    const val androidxAppcompatResourcesLib = "androidx.appcompat:appcompat-resources:${VersionsJetpack.androidxAppcompat}"

    const val androidxRoomLib = "androidx.room:room-runtime:${VersionsJetpack.androidxRoom}"
    const val androidxRoomKtx = "androidx.room:room-ktx:${VersionsJetpack.androidxRoom}"
    const val androidxRoomRx = "androidx.room:room-ktx:${VersionsJetpack.androidxRoom}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${VersionsJetpack.androidxRoom}"

    const val androidxLifecycleLib = "androidx.lifecycle:lifecycle-extensions:${VersionsJetpack.androidxLifecycle}"
    const val androidxLifecycleRx = "androidx.lifecycle:lifecycle-reactivestreams:${VersionsJetpack.androidxLifecycle}"
    const val androidxLifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${VersionsJetpack.androidxLifecycle}"
    const val androidxLifecycleSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${VersionsJetpack.androidxLifecycleSavedState}"

    const val androidxNavigationFragmentLib = "androidx.navigation:navigation-fragment-ktx:${VersionsJetpack.androidxNavigation}"
    const val androidxNavigationUiLib = "androidx.navigation:navigation-ui-ktx:${VersionsJetpack.androidxNavigation}"
    const val androidxNavigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${VersionsJetpack.androidxNavigation}"

    const val androidxWorkManagerLib = "androidx.work:work-runtime-ktx:${VersionsJetpack.androidxWorkManager}"
    const val androidxWorkManagerRx = "androidx.work:work-rxjava2:${VersionsJetpack.androidxWorkManager}"

    const val androidxCardviewLib = "androidx.cardview:cardview:${VersionsJetpack.androidx}"
    const val androidxVectorDrawableLib = "androidx.vectordrawable:vectordrawable:${VersionsJetpack.androidx}"
    const val androidxMaterialLib = "com.google.android.material:material:${VersionsJetpack.androidxMaterial}"
    const val androidxConstraintLayoutLib = "androidx.constraintlayout:constraintlayout:${VersionsJetpack.androidxConstraintLayout}"
    const val androidxLegacySupportLib = "androidx.legacy:legacy-support-v4:${VersionsJetpack.androidxLegacySupport}"

    const val playServiceMapsLib = "com.google.android.gms:play-services-maps:${VersionsJetpack.playServiceMaps}"
    const val playServicesLocation = "com.google.android.gms:play-services-location:${VersionsJetpack.playServices}"

    const val mapsUtilsLib = "com.google.maps.android:android-maps-utils:${VersionsJetpack.mapsUtils}"

    const val multidexLib = "com.android.support:multidex:${VersionsJetpack.multidex}"
}