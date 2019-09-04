package pl.mobite.sample.security.uscases

import android.os.Build
import androidx.annotation.RequiresApi
import pl.mobite.sample.security.data.local.EncryptionPreferences
import pl.mobite.sample.security.encryption.CipherWrapper
import pl.mobite.sample.security.encryption.KeystoreWrapper
import javax.crypto.Cipher
import javax.crypto.SecretKey

abstract class GenerateSecretKeyUseCase: (String) -> Unit
abstract class RemoveSecretKeyUseCase: (String) -> Unit
abstract class GetSecretKeyUseCase: (String) -> SecretKey?
abstract class EncryptUseCase: (String, SecretKey) -> String
abstract class DecryptUseCase: (String, SecretKey) -> String

class GenerateSecretKeyUseCaseImpl(
    private val keystoreWrapper: KeystoreWrapper,
    private val cipherWrapper: CipherWrapper,
    private val encryptionPreferences: EncryptionPreferences
): GenerateSecretKeyUseCase() {

    override fun invoke(alias: String) {
        val keyPair = keystoreWrapper.generateAsymmetricKey(alias)
        val secretKey = keystoreWrapper.generateDefaultSymmetricKey()
        encryptionPreferences.encryptedSecretKey = cipherWrapper.wrapKey(secretKey, keyPair.public)
    }
}

@RequiresApi(Build.VERSION_CODES.M)
class GenerateSecretKeyUseCaseApi23Impl(
    private val keystoreWrapper: KeystoreWrapper
): GenerateSecretKeyUseCase() {

    override fun invoke(alias: String) {
        keystoreWrapper.generateSymmetricKeyApi23(alias)
    }
}

class GetSecretKeyUseCaseImpl(
    private val keystoreWrapper: KeystoreWrapper,
    private val cipherWrapper: CipherWrapper,
    private val encryptionPreferences: EncryptionPreferences
): GetSecretKeyUseCase() {

    override fun invoke(alias: String): SecretKey? {
        val encryptedSecretKey = encryptionPreferences.encryptedSecretKey
        val keyPair = keystoreWrapper.getAsymmetricKeyPair(alias)
        return if (encryptedSecretKey != null && keyPair != null) {
            cipherWrapper.unWrapKey(encryptedSecretKey, "AES", Cipher.SECRET_KEY, keyPair.private) as SecretKey
        } else {
            null
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
class GetSecretKeyUseCaseApi23Impl(
    private val keystoreWrapper: KeystoreWrapper
): GetSecretKeyUseCase() {

    override fun invoke(alias: String): SecretKey? {
        return keystoreWrapper.getSymmetricKey(alias)
    }
}

class RemoveSecretKeyUseCaseImpl(
    private val keystoreWrapper: KeystoreWrapper,
    private val encryptionPreferences: EncryptionPreferences
): RemoveSecretKeyUseCase() {

    override fun invoke(alias: String) {
        keystoreWrapper.removeKey(alias)
        encryptionPreferences.clear()
    }
}

class EncryptUseCaseImpl(
    private val cipherWrapper: CipherWrapper,
    private val encryptionPreferences: EncryptionPreferences
): EncryptUseCase() {

    override fun invoke(message: String, secretKey: SecretKey): String {
        val (encryptedMessage, initializationVector) = cipherWrapper.encrypt(message, secretKey)
        encryptionPreferences.initializationVector = initializationVector
        return encryptedMessage
    }
}

class DecryptUseCaseImpl(
    private val cipherWrapper: CipherWrapper,
    private val encryptionPreferences: EncryptionPreferences
): DecryptUseCase() {

    override fun invoke(message: String, secretKey: SecretKey): String {
        val initializationVector = encryptionPreferences.initializationVector ?: throw Exception("Missing initialization vector")
        return cipherWrapper.decrypt(message, initializationVector, secretKey)
    }
}
