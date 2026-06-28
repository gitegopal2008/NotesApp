package com.notesapp.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context)
            .setKeyGenParameterSpec(
                KeyGenParameterSpec.Builder(
                    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build()
            )
            .build()
    }

    fun encryptString(plaintext: String): String {
        val inputFile = File(context.cacheDir, "plain_temp")
        val outputFile = File(context.cacheDir, "enc_temp")
        try {
            inputFile.writeText(plaintext)
            val encryptedFile = EncryptedFile.Builder(
                context, outputFile, masterKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
            inputFile.inputStream().use { input ->
                encryptedFile.openFileOutput().use { output -> input.copyTo(output) }
            }
            return outputFile.readBytes().toString(Charsets.ISO_8859_1)
        } finally {
            inputFile.delete()
            outputFile.delete()
        }
    }

    fun decryptString(ciphertext: String): String {
        val inputFile = File(context.cacheDir, "enc_dec_temp")
        val outputFile = File(context.cacheDir, "dec_temp")
        try {
            inputFile.writeBytes(ciphertext.toByteArray(Charsets.ISO_8859_1))
            val encryptedFileBuilder = EncryptedFile.Builder(
                context, inputFile, masterKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
            encryptedFileBuilder.openFileInput().use { input ->
                outputFile.outputStream().use { output -> input.copyTo(output) }
            }
            return outputFile.readText()
        } finally {
            inputFile.delete()
            outputFile.delete()
        }
    }

    fun encryptNoteContent(content: String): String = encryptString(content)
    fun decryptNoteContent(content: String): String = decryptString(content)
}
