package com.kio.qitinterview.job.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter
import com.kio.qitinterview.job.application.port.out.JobPositionRepository

@PersistenceAdapter
class JobPositionRepositoryImpl(
    private val jpaJobPositionRepository: JpaJobPositionRepository
): JobPositionRepository {
    override fun findByName(name: String) = jpaJobPositionRepository.findByName(name)
}