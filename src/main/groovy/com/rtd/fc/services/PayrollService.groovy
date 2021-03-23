package com.rtd.fc.services

import org.springframework.stereotype.Service

@Service
class PayrollService {
    def calculateSalaries( def players ) {
        return [
                [ nombre: "Juan Perez",     goles_minimos: 15, goles: 10, sueldo: 50000,  bono: 25000, sueldo_completo: 67875,  equipo: "rojo" ],
                [ nombre: "El Rulo",        goles_minimos: 10, goles: 9,  sueldo: 30000,  bono: 15000, sueldo_completo: 42450,  equipo: "rojo" ],
                [ nombre: "EL Cuauh",       goles_minimos: 20, goles: 30, sueldo: 100000, bono: 30000, sueldo_completo: 130000, equipo: "azul" ],
                [ nombre: "Cosme Fulanito", goles_minimos: 5,  goles: 7,  sueldo: 20000,  bono: 10000, sueldo_completo: 30000,  equipo: "azul" ]
        ]
    }
}
