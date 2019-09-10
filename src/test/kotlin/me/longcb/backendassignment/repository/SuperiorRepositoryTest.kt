package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.repository.impl.SuperiorRepositoryImpl
import me.longcb.backendassignment.utils.fakeAuditTime
import me.longcb.backendassignment.utils.persist
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
class SuperiorRepositoryTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    private val repository: SuperiorRepositoryImpl by lazy {
        SuperiorRepositoryImpl()
    }

    @BeforeEach
    fun beforeEach() {
        repository.setEntityManager(entityManager)
    }

    @Test
    fun givenStaffHas2Superiors_whenGetSuperiors_thenReturn2Superiors() {
        val (subordinate, rightSuperiors) = fakeASubordinateAnd2Superiors()
        val superiors = repository.getSuperiors(subordinate.name)
        assertThat(superiors[0].name).isEqualTo(rightSuperiors[0].name)
        assertThat(superiors[1].name).isEqualTo(rightSuperiors[1].name)
    }

    @Test
    fun givenStaffHasNoSuperior_whenGetSuperiors_thenReturnEmpty() {
        val staff = fakeAStaff()
        val superiors = repository.getSuperiors(staff.name)
        assertThat(superiors.size).isEqualTo(0)
    }

    @Test
    fun givenStaffHasASuperiorAndASuperSuperior_whenGetSuperiors_thenReturn2Superiors() {
        val (subordinate, rightSuperiors) = fakeASubordinateASuperiorAndASuperSuperior()
        val superiors = repository.getSuperiors(subordinate.name)
        assertThat(superiors[0].name).isEqualTo(rightSuperiors[0].name)
        assertThat(superiors[1].name).isEqualTo(rightSuperiors[1].name)
    }

    fun fakeAStaff(): StaffEntity {
        val staff = StaffEntity("A")
        fakeAuditTime(staff)
        entityManager.persist(staff)
        return staff
    }

    fun fakeASubordinateAnd2Superiors(): Pair<StaffEntity, List<StaffEntity>> {
        val sub1 = StaffEntity("A")
        val sup1 = StaffEntity("B")
        val sup2 = StaffEntity("C")
        val rel1 = RelationEntity("B", "A")
        val rel2 = RelationEntity("C", "A")
        fakeAuditTime(sub1, sup1, sup2, rel1, rel2)
        persist(entityManager, sub1, sup1, sup2, rel1, rel2)
        return Pair(sub1, listOf(sup1, sup2))
    }

    fun fakeASubordinateASuperiorAndASuperSuperior(): Pair<StaffEntity, List<StaffEntity>> {
        val sub1 = StaffEntity("A")
        val sup1 = StaffEntity("B")
        val sup2 = StaffEntity("C")
        val rel1 = RelationEntity("B", "A")
        val rel2 = RelationEntity("C", "B")
        fakeAuditTime(sub1, sup1, sup2, rel1, rel2)
        persist(entityManager, sub1, sup1, sup2, rel1, rel2)
        return Pair(sub1, listOf(sup1, sup2))
    }
}