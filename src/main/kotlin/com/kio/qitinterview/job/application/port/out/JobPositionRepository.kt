package com.kio.qitinterview.job.application.port.out

import com.kio.qitinterview.job.domain.model.JobPosition

interface JobPositionRepository {
    fun findByName(name: String): JobPosition?
}