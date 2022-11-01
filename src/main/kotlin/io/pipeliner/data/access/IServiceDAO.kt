package io.pipeliner.data.access

import io.pipeliner.domain.objects.service.Service
import io.pipeliner.domain.objects.service.ServiceCreation

interface IServiceDAO {
    fun create(serviceCreation: ServiceCreation): Service
    fun update(service: Service): Service
    fun findByName(name: String): Service?
    fun findById(id: String): Service?
}
