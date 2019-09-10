package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.exception.CustomException
import me.longcb.backendassignment.repository.RelationRepository
import me.longcb.backendassignment.repository.StaffRepository
import me.longcb.backendassignment.service.impl.HierarchyServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class HierarchyServiceTest {
    @InjectMocks
    private lateinit var hierarchyService: HierarchyServiceImpl

    @Spy
    private lateinit var relationRepository: RelationRepository

    @Spy
    private lateinit var staffRepository: StaffRepository

    @Test
    fun givenHashHierarchy_whenBuildRelationship_thenReplaceCurrentHierarchy() {
        hierarchyService.buildRelationship(createHashHierarchy())
        Mockito.verify(relationRepository).deleteAll()
        Mockito.verify(relationRepository).saveAll(ArgumentMatchers.anyList())
        Mockito.verify(staffRepository).deleteAll()
        Mockito.verify(staffRepository).saveAll(ArgumentMatchers.anyList())
    }

    @Test
    fun givenReallyBigHierarchy_whenBuildRelationship_thenProcessEfficient() {
        val tree = hierarchyService.buildRelationship(createBigHashHierarchy())
        assertThat(tree.toJsonString()).isNotBlank()
    }

    @Test
    fun givenEmptyHierarchy_whenBuildHierarchy_thenReturnEmpty() {
        Mockito.doReturn(emptyList<RelationEntity>()).`when`(relationRepository).findAll()
        Mockito.doReturn(emptyList<StaffEntity>()).`when`(staffRepository).findAll()
        assertThrows<CustomException>("Hierarchy is empty") { hierarchyService.buildHierarchy() }
    }

    @Test
    fun givenValidHierarchy_whenBuildHierarchy_thenReturnRightTree() {
        Mockito.doReturn(listOf(RelationEntity("a", "b"))).`when`(relationRepository).findAll()
        Mockito.doReturn(listOf(StaffEntity("a"), StaffEntity("b"))).`when`(staffRepository).findAll()
        assertEquals(hierarchyService.buildHierarchy().toJsonString(), """
            { "a": { "b": {  } } }
        """.trimIndent())

    }

    private fun createHashHierarchy() = HashMap<String, String>().also {
        it["Pete"] = "Nick"
        it["Barbara"] = "Nick"
        it["Nick"] = "Sophie"
        it["Sophie"] = "Jonas"
    }

    private fun createBigHashHierarchy(): HashMap<String, String> {
        val hash = HashMap<String, String>()
        for (i in 1 until 1000) {
            hash["$i"] = "${i + 1}"
        }
        return hash
    }
}
