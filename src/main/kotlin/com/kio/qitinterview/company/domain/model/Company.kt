package com.kio.qitinterview.company.domain.model

import com.kio.qit.converter.StringListConverter
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "company")
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("회사 이름")
    @Column(nullable = false, unique = true)
    val name: String,

    @Comment("회사 별칭들")
    @Column(columnDefinition = "json")
    @Convert(converter = StringListConverter::class)
    val aliases: List<String> = emptyList()
) {
}