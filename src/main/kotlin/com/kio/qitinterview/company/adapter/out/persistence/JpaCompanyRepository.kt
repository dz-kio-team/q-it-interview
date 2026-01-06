package com.kio.qitinterview.company.adapter.out.persistence

import com.kio.qitinterview.company.domain.model.Company
import org.springframework.data.jpa.repository.JpaRepository

interface JpaCompanyRepository : JpaRepository<Company, Long> {
    fun findByName(name: String): Company?
}