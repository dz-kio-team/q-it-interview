package com.kio.qitinterview.company.application.port.out

import com.kio.qitinterview.company.domain.model.Company

interface CompanyRepository {
    fun findByName(name: String): Company?
}