package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.repository.SuperiorRepository
import me.longcb.backendassignment.service.impl.StaffServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.exceptions.misusing.PotentialStubbingProblem
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StaffServiceTest {
    @InjectMocks
    private lateinit var staffService: StaffServiceImpl

    @Mock
    private lateinit var superiorRepository: SuperiorRepository

    @BeforeEach
    fun setup() {
        val superior = StaffEntity("Boss")
        Mockito.`when`(superiorRepository.getSuperiors("Employee")).thenReturn(listOf(superior))
    }

    @Test
    fun whenValidStaffName_thenBossShouldBeFound() {
        val superiors = staffService.getSuperiors("Employee")
        assertThat(superiors[0].name).isEqualTo("Boss")
    }

    @Test
    fun whenInvalidStaffName_thenBossShouldNotBeFound() {
        assertThrows<PotentialStubbingProblem> { staffService.getSuperiors("Non existed Employee") }
    }
}