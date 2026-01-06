package com.kio.qitinterview.member.application.port.out

import com.kio.qitinterview.member.domain.model.Member

interface MemberRepository {
    fun saveMember(member: Member): Member
    fun findById(memberId: Long): Member?
}