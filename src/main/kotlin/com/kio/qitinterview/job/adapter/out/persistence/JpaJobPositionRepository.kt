package com.kio.qitinterview.job.adapter.out.persistence

import com.kio.qitinterview.job.domain.model.JobPosition
import org.springframework.data.jpa.repository.JpaRepository

interface JpaJobPositionRepository : JpaRepository<JobPosition, Long> {
    fun findByName(name: String): JobPosition?
}