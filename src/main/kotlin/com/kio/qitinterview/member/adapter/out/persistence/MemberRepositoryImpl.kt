package com.kio.qitinterview.member.adapter.out.persistence

import com.kio.qit.annotation.PersistenceAdapter
import com.kio.qitinterview.member.application.port.out.MemberRepository
import com.kio.qitinterview.member.domain.model.Member
import org.springframework.data.repository.findByIdOrNull

@PersistenceAdapter
class MemberRepositoryImpl(
    private val jpaMemberRepository: JpaMemberRepository
): MemberRepository {
    override fun saveMember(member: Member) = jpaMemberRepository.save(member)
    override fun findById(memberId: Long) = jpaMemberRepository.findByIdOrNull(memberId)
}