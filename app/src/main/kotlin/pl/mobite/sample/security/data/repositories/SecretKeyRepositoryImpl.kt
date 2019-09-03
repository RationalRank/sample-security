package pl.mobite.sample.security.data.repositories

import org.koin.core.KoinComponent
import org.koin.core.inject
import pl.mobite.sample.security.data.local.EncryptionPreferences
import pl.mobite.sample.security.encryption.CipherWrapper
import pl.mobite.sample.security.encryption.KeystoreWrapper
import javax.crypto.Cipher
import javax.crypto.SecretKey


class SecretKeyRepositoryImpl: SecretKeyRepository, KoinComponent {

    private val keystoreWrapper: KeystoreWrapper by inject()
    private val cipherWrapper: CipherWrapper by inject()
    private val encryptionPreferences: EncryptionPreferences by inject()

    override fun checkKey(keyAlias: String): Boolean {
        return keystoreWrapper.getAsymmetricKeyPair(keyAlias) != null
                && encryptionPreferences.encryptedSecretKey != null
    }

    override fun generateKey(keyAlias: String) {
        val keyPair = keystoreWrapper.generateAsymmetricKey(keyAlias)
        val secretKey = keystoreWrapper.generateDefaultSymmetricKey()
        encryptionPreferences.encryptedSecretKey = cipherWrapper.wrapKey(secretKey, keyPair.public)
    }

    override fun removeKey(keyAlias: String) {
        keystoreWrapper.removeKey(keyAlias)
        encryptionPreferences.clear()
    }

    override fun encrypt(keyAlias: String, message: String): String {
        return getSecretKey(keyAlias)?.let {
            val (encryptedMessage, initializationVector) = cipherWrapper.encrypt(message, it)
            encryptionPreferences.initializationVector = initializationVector
            return encryptedMessage
        } ?: throw Exception("Secret key not generated for alias: $keyAlias")
    }

    override fun decrypt(keyAlias: String, message: String): String {
        val secretKey: SecretKey = getSecretKey(keyAlias) ?: throw Exception("Secret key not generated for alias: $keyAlias")
        val initializationVector: String = encryptionPreferences.initializationVector ?: throw Exception("Missing initialization vector")
        return cipherWrapper.decrypt(message, initializationVector, secretKey)
    }

    private fun getSecretKey(keyAlias: String): SecretKey? {
        val encryptedSecretKey = encryptionPreferences.encryptedSecretKey
        val keyPair = keystoreWrapper.getAsymmetricKeyPair(keyAlias)
        return if (encryptedSecretKey != null && keyPair != null) {
            cipherWrapper.unWrapKey(encryptedSecretKey, "AES", Cipher.SECRET_KEY, keyPair.private) as SecretKey
        } else {
            null
        }
    }

}