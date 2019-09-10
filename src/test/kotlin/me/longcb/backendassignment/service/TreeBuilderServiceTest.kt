package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.exception.CustomException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TreeBuilderServiceTest {
    private val pete by lazy { StaffEntity("Pete") }
    private val nick by lazy { StaffEntity("Nick") }
    private val barbara by lazy { StaffEntity("Barbara") }
    private val sophie by lazy { StaffEntity("Sophie") }
    private val jonas by lazy { StaffEntity("Jonas") }

    @Test
    fun givenRightHierarchy_whenBuildTree_thenReturnRightTree() {
        val (staffs, relations) = createRightHierarchy()
        val tree = TreeBuilderService(staffs, relations).build()
        assertEquals(tree.toJsonString(), """
            { "Jonas": { "Sophie": { "Nick": { "Pete": {  }, "Barbara": {  } } } } }
        """.trimIndent())
    }

    @Test
    fun givenMultiRootHierarchy_whenBuildTree_thenReturnError() {
        val (staffs, relations) = createMultiRootHierarchy()
        assertThrows<CustomException>("contain multiple root") {
            TreeBuilderService(staffs, relations).build()
        }
    }

    @Test
    fun givenConflictHierarchy_whenBuildTree_thenReturnError() {
        val (staffs, relations) = createConflictHierarchy()
        assertThrows<CustomException>("Sophie cannot be supervisor of Jonas, because\nJonas is supervisor of Sophie,\n") {
            TreeBuilderService(staffs, relations).build()
        }
    }

    @Test
    fun givenSelfEmployedHierarchy_whenBuildTree_thenReturnError() {
       val relation = RelationEntity("Jonas", "Jonas")
        assertThrows<CustomException>("Jonas cannot be superior for himself/herself") {
            TreeBuilderService(listOf(jonas), listOf(relation)).build()
        }
    }

    @Test
    fun givenEmptyHierarchy_whenBuildTree_thenReturnError() {
        assertThrows<CustomException> {
            TreeBuilderService(emptyList(), emptyList()).build()
        }
    }

    private fun createRightHierarchy(): Pair<List<StaffEntity>, List<RelationEntity>> {
        val r1 = RelationEntity("Nick", "Pete")
        val r2 = RelationEntity("Nick", "Barbara")
        val r3 = RelationEntity("Sophie", "Nick")
        val r4 = RelationEntity("Jonas", "Sophie")
        return Pair(listOf(pete, nick, barbara, sophie, jonas), listOf(r1, r2, r3, r4))
    }

    private fun createMultiRootHierarchy(): Pair<List<StaffEntity>, List<RelationEntity>> {
        val r1 = RelationEntity("Nick", "Pete")
        val r2 = RelationEntity("Nick", "Barbara")
        val r3 = RelationEntity("Sophie", "Nick")
        val r4 = RelationEntity("Jonas", "Sophie")
        val top = StaffEntity("top")
        val long = StaffEntity("long")
        val r5 = RelationEntity("top", "long")
        return Pair(listOf(pete, nick, barbara, sophie, jonas, top, long), listOf(r1, r2, r3, r4, r5))
    }

    private fun createConflictHierarchy(): Pair<List<StaffEntity>, List<RelationEntity>> {
        val r1 = RelationEntity("Nick", "Pete")
        val r2 = RelationEntity("Nick", "Barbara")
        val r3 = RelationEntity("Sophie", "Nick")
        val r4 = RelationEntity("Jonas", "Sophie")
        val r5 = RelationEntity("Sophie", "Jonas")
        return Pair(listOf(pete, nick, barbara, sophie, jonas), listOf(r1, r2, r3, r4, r5))
    }
}