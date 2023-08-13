package dev.aliakbar.tmdbunofficial.data.source.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import dev.aliakbar.tmdbunofficial.ConfigurationPreferences
import java.io.InputStream
import java.io.OutputStream

object ConfigurationPreferencesSerializer: Serializer<ConfigurationPreferences>
{
    override val defaultValue: ConfigurationPreferences = ConfigurationPreferences.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): ConfigurationPreferences
    {
        try
        {
            return ConfigurationPreferences.parseFrom(input)
        }
        catch (exception: InvalidProtocolBufferException)
        {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ConfigurationPreferences, output: OutputStream)
    {
        t.writeTo(output)
    }

}
