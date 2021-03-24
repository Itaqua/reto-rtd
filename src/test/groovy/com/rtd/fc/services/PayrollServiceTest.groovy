package com.rtd.fc.services

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import sun.security.x509.OtherName

@SpringBootTest
class PayrollServiceTest {
    @Autowired
    PayrollService payrollService

    @Test
    void "Calculate salaries"() {
        when: 'We receive a payroll list to calculate'
        def players = [
                [ nombre: "Juan Perez",     nivel: "C",     goles: 10, sueldo: 50000,  bono: 25000, sueldo_completo: null, equipo: "rojo" ],
                [ nombre: "EL Cuauh",       nivel: "Cuauh", goles: 30, sueldo: 100000, bono: 30000, sueldo_completo: null, equipo: "azul" ],
                [ nombre: "Cosme Fulanito", nivel: "A",     goles: 7,  sueldo: 20000,  bono: 10000, sueldo_completo: null, equipo: "azul" ],
                [ nombre: "El Rulo",        nivel: "B",     goles: 9,  sueldo: 30000,  bono: 15000, sueldo_completo: null, equipo: "rojo" ]
        ]

        def salaries = payrollService.calculateSalaries(players)

        then:
        assert salaries == [
                [ nombre: "Juan Perez",     goles_minimos: 15, goles: 10, sueldo: 50000,  bono: 25000, sueldo_completo: 67833.33,  equipo: "rojo" ],
                [ nombre: "El Rulo",        goles_minimos: 10, goles: 9,  sueldo: 30000,  bono: 15000, sueldo_completo: 42450,  equipo: "rojo" ],
                [ nombre: "EL Cuauh",       goles_minimos: 20, goles: 30, sueldo: 100000, bono: 30000, sueldo_completo: 130000, equipo: "azul" ],
                [ nombre: "Cosme Fulanito", goles_minimos: 5,  goles: 7,  sueldo: 20000,  bono: 10000, sueldo_completo: 30000,  equipo: "azul" ]
        ]
    }

    @Test
    void "check service ratio logic"() {
        def target = 10
        def scored
        def ratio

        when: "scored is greater than Zero but less than Target"
            scored = 5
            ratio = payrollService.scoreRatio(scored, target)
        then: "ratio is 50%"
            assert ratio == 0.5

        when: "scored is equals to Target"
            scored = 10
            ratio = payrollService.scoreRatio(scored, target)
        then: "ratio is 100%"
            assert ratio == 1

        when: "scored is greater than Target"
            scored = 15
            ratio = payrollService.scoreRatio(scored, target)
        then: "ratio is 100% as the maximum"
            assert ratio == 1
    }

}
