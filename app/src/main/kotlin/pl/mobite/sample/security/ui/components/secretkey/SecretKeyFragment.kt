package pl.mobite.sample.security.ui.components.secretkey

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_secret_key.*
import pl.mobite.sample.security.R
import pl.mobite.sample.security.ui.base.mvi.provide
import pl.mobite.sample.security.ui.custom.CustomTextWatcher
import pl.mobite.sample.security.utils.setVisibleOrGone


class SecretKeyFragment: Fragment() {

    private val viewModel by lazy { provide(SecretKeyViewModel::class.java) }
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_secret_key, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageInput.addTextChangedListener(object: CustomTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                encryptButton.isEnabled = s?.toString()?.isNotBlank() ?: false
            }
        })

        generateKeyButton.setOnClickListener { viewModel.generateNewKey() }

        removeKeyButton.setOnClickListener { viewModel.removeKey() }

        encryptButton.setOnClickListener {
            viewModel.encryptMessage(messageInput.text.toString())
        }

        decryptButton.setOnClickListener {
            viewModel.decryptMessage(encryptedMessageText.text.toString())
        }

        clearMessagesButton.setOnClickListener { viewModel.clearMessage() }
    }

    override fun onStart() {
        super.onStart()
        viewModel.subscribe(::render).addTo(compositeDisposable)
        viewModel.onStart()
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }

    private fun render(viewState: SecretKeyViewState) {
        with(viewState) {
            errorEvent?.consume {
                it.printStackTrace()
                Toast.makeText(activity, R.string.error_message, Toast.LENGTH_SHORT).show()
            }

            val hasSecretKey = secretKeyAlias != null
            val hasEncryptedMessage = messageEncrypted != null
            val hasDecryptedMessage = messageDecrypted != null

            keyAliasLabel.text = getString(
                R.string.label_key_alias,
                if (hasSecretKey) secretKeyAlias else getString(R.string.label_key_missing)
            )

            generateKeyButton.setVisibleOrGone(!hasSecretKey)
            removeKeyButton.setVisibleOrGone(hasSecretKey)
            clearMessagesButton.setVisibleOrGone(hasSecretKey)
            messageInputLayout.setVisibleOrGone(hasSecretKey)
            encryptButton.setVisibleOrGone(hasSecretKey)
            encryptedMessageLabel.setVisibleOrGone(hasSecretKey && hasEncryptedMessage)
            encryptedMessageText.setVisibleOrGone(hasSecretKey && hasEncryptedMessage)
            decryptButton.setVisibleOrGone(hasSecretKey && hasEncryptedMessage)
            decryptedMessageLabel.setVisibleOrGone(hasSecretKey && hasDecryptedMessage)
            decryptedMessageText.setVisibleOrGone(hasSecretKey && hasDecryptedMessage)

            encryptedMessageText.text = messageEncrypted.orEmpty()
            decryptedMessageText.text = messageDecrypted

            clearEvent?.consume {
                messageInput.text?.clear()
            }

            generateKeyButton.isEnabled = !isLoading
            removeKeyButton.isEnabled = !isLoading
            clearMessagesButton.isEnabled = !isLoading
            messageInput.isEnabled = !isLoading
            encryptButton.isEnabled = !isLoading && messageInput.text?.isNotBlank() ?: false
            decryptButton.isEnabled = !isLoading
        }
    }
}