package com.kio.qitinterview.company.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter
import com.kio.qitinterview.company.application.port.out.CompanyRepository

@PersistenceAdapter
class CompanyRepositoryImpl(
    private val jpaCompanyRepository: JpaCompanyRepository
) : CompanyRepository {
    override fun findByName(name: String) = jpaCompanyRepository.findByName(name)
}