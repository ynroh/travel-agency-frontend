package com.shadow_shift_studio.travelagency_frontend.data.client

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.data.secure_data.TokensStore

object KeyStoreManager {
    var keyStore: TokensStore? = null
    var accessToken: ByteArray = ByteArray(16)
    var refreshToken: ByteArray = ByteArray(16)

    fun getKeyStore(context: Context): TokensStore {
        if (keyStore == null) {
            keyStore = TokensStore(context)
        }
        return keyStore!!
    }

    fun getDecryptAccessKey(keyAlias: String): String {
        return keyStore?.decryptData(keyAlias, accessToken) ?: ""
    }

    fun getDecryptKey(keyAlias: String): String {
        return keyStore?.decryptData(keyAlias, refreshToken) ?: ""
    }
}