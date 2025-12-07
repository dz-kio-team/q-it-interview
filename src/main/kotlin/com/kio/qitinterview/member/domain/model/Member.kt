package com.kio.qitinterview.member.domain.model

import com.kio.qit.domain.model.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val careerYears: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, unique = true)
    val nickname: String,

    @Column(nullable = false)
    val emailVerified: Boolean = false,

    val profileImageUrl: String? = null,

    val lastLoginAt: LocalDateTime? = null

): BaseEntity() {}