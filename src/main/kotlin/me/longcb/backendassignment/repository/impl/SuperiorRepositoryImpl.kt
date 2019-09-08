package me.longcb.backendassignment.repository.impl

import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.repository.SuperiorRepository
import org.hibernate.Session
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class SuperiorRepositoryImpl : SuperiorRepository {
    private lateinit var entityManager: EntityManager

    private val superiorsSQL = """
    SELECT 
        staffs.*
    FROM
        staffs
    WHERE
        (name IN (SELECT 
            superior
            FROM
                staffs
                    INNER JOIN
                relations ON relations.subordinate = staffs.name
            WHERE
                staffs.name = :name UNION SELECT 
                B.superior
            FROM
                (SELECT 
                    superior
                FROM
                    staffs
                INNER JOIN relations ON relations.subordinate = staffs.name
                WHERE
                    staffs.name = :name) AS A
                    LEFT JOIN
                relations AS B ON A.superior = B.subordinate))
    """

    override fun getSuperiors(name: String): List<StaffEntity> {
        val session = getCurrentSession()
        val query = session.createNativeQuery(superiorsSQL, StaffEntity::class.java)
        query.setParameter("name", name)
        @Suppress("UNCHECKED_CAST") return  query.resultList as List<StaffEntity>
    }

    private fun getCurrentSession(): Session {
        return entityManager.unwrap(Session::class.java)
    }

    @PersistenceContext
    fun setEntityManager(entityManager: EntityManager) {
        this.entityManager = entityManager
    }
}
