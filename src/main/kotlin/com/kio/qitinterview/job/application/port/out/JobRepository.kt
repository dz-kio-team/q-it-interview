package com.kio.qitinterview.job.application.port.out

import com.kio.qitinterview.job.domain.model.JobGroup
import com.kio.qitinterview.job.domain.model.JobPosition

interface JobRepository {
    fun findJobPositionByName(name: String): JobPosition?
    fun findJobGroupByName(name: String): JobGroup?
    fun saveJobPosition(jobPosition: JobPosition): JobPosition
    fun saveJobGroup(jobGroup: JobGroup): JobGroup
}