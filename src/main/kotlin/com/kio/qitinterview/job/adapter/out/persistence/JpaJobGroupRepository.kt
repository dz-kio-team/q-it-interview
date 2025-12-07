package com.kio.qitinterview.job.adapter.out.persistence

import com.kio.qitinterview.job.domain.model.JobGroup
import org.springframework.data.jpa.repository.JpaRepository

interface JpaJobGroupRepository: JpaRepository<JobGroup, Long> {
}