package com.kio.qitinterview.member.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val careerYears: Long
) {}