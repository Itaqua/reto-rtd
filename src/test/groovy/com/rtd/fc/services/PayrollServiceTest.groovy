package com.rtd.fc.services

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PayrollServiceTest {
    @Autowired
    PayrollService payrollService

    @Test
    void "Calculate payrolls"() {
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
                [ nombre: "Juan Perez",     goles_minimos: 15, goles: 10, sueldo: 50000,  bono: 25000, sueldo_completo: 67875,  equipo: "rojo" ],
                [ nombre: "El Rulo",        goles_minimos: 10, goles: 9,  sueldo: 30000,  bono: 15000, sueldo_completo: 42450,  equipo: "rojo" ],
                [ nombre: "EL Cuauh",       goles_minimos: 20, goles: 30, sueldo: 100000, bono: 30000, sueldo_completo: 130000, equipo: "azul" ],
                [ nombre: "Cosme Fulanito", goles_minimos: 5,  goles: 7,  sueldo: 20000,  bono: 10000, sueldo_completo: 30000,  equipo: "azul" ]
        ]
    }

}
