package dev.aliakbar.tmdbunofficial.data.source.local

import dev.aliakbar.tmdbunofficial.data.source.network.NetworkConfiguration

interface ConfigurationRepository
{
    suspend fun insertConfiguration(configuration: LocalConfiguration)
    suspend fun deleteConfiguration(configuration: LocalConfiguration)
    suspend fun updateConfiguration(configuration: LocalConfiguration)
    fun getConfiguration(id: Int): LocalConfiguration
    fun getAllConfiguration(): List<LocalConfiguration>
}
class OfflineConfigurationRepository(
    private val configurationDao: LocalConfigurationDao
): ConfigurationRepository
{
    override suspend fun insertConfiguration(configuration: LocalConfiguration)
    {
        configurationDao.insert(configuration)
    }

    override suspend fun deleteConfiguration(configuration: LocalConfiguration)
    {
        configurationDao.delete(configuration)
    }

    override suspend fun updateConfiguration(configuration: LocalConfiguration)
    {
        configurationDao.update(configuration)
    }

    override fun getConfiguration(id: Int): LocalConfiguration
    {
        return configurationDao.getConfiguration(id)
    }

    override fun getAllConfiguration(): List<LocalConfiguration>
    {
        return configurationDao.getAllConfigurations()
    }
}