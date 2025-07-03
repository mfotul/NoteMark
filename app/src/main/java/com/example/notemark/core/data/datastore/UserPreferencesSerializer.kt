package com.example.notemark.core.data.datastore

import androidx.datastore.core.Serializer
import com.example.notemark.note.domain.DataStoreSettings
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

object UserPreferencesSerializer: Serializer<DataStoreSettings> {
    override val defaultValue: DataStoreSettings
        get() = DataStoreSettings()

    override suspend fun readFrom(input: InputStream): DataStoreSettings {
        val encryptedBytes = input.use { it.readBytes() }
        val encryptedBytesEncoded = Base64.getDecoder().decode(encryptedBytes)
        val decryptedBytes = Crypto.decrypt(encryptedBytesEncoded)
        return Json.decodeFromString(decryptedBytes.decodeToString())
    }

    override suspend fun writeTo(t: DataStoreSettings, output: OutputStream) {
        val encryptedBytes = Crypto.encrypt(Json.encodeToString(t).toByteArray())
        val encryptedBytesBase64 = Base64.getEncoder().encode(encryptedBytes)
        output.use {
            it.write(encryptedBytesBase64)
        }
    }

}