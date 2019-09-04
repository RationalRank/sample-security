package pl.mobite.sample.security

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.mobite.sample.security.di.appModule
import pl.mobite.sample.security.di.encryptionModule
import pl.mobite.sample.security.di.secretKeyUseCasesModule


class SecurityApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@SecurityApp)

            modules(appModule, secretKeyUseCasesModule, encryptionModule)
        }
    }

    companion object {

        @JvmStatic
        lateinit var instance: SecurityApp
            private set
    }
}