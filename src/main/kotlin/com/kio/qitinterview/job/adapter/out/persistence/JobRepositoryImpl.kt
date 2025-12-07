package com.kio.qitinterview.job.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter
import com.kio.qitinterview.job.application.port.out.JobRepository
import com.kio.qitinterview.job.domain.model.JobGroup
import com.kio.qitinterview.job.domain.model.JobPosition

@PersistenceAdapter
class JobRepositoryImpl(
    private val jpaJobPositionRepository: JpaJobPositionRepository,
    private val jpaJobGroupRepository: JpaJobGroupRepository
): JobRepository {
    override fun findJobPositionByName(name: String) = jpaJobPositionRepository.findByName(name)
    override fun saveJobPosition(jobPosition: JobPosition) = jpaJobPositionRepository.save(jobPosition)
    override fun saveJobGroup(jobGroup: JobGroup) = jpaJobGroupRepository.save(jobGroup)
}