package com.rtd.fc.services

import org.springframework.stereotype.Service

@Service
class PayrollService {
    def defaultCategoryObjectives = [
        'default': [
            'A' : 5,
            'B' : 10,
            'C' : 15,
            'Cuauh' : 20
        ]
    ]

    def calculateSalaries( List<Map> players, Map categoryObjectives = null) {
        if ( categoryObjectives == null ) {
            categoryObjectives = defaultCategoryObjectives
        }

        def teamGroups = players.groupBy { it.equipo }

        teamGroups.collect {team, teamPlayers ->

            def teamCategoryObjectives = categoryObjectives[team] ?: categoryObjectives?.default ?: []

            def expandedTeamPlayers = teamPlayers.collect { player ->
                [
                        nombre: player.nombre,
                        goles_minimos: teamCategoryObjectives[player.nivel] ?: 0, // if not defined???
                        goles: player.goles,
                        sueldo: player.sueldo,
                        bono: player.bono,
                        sueldo_completo: player.sueldo_completo,
                        equipo: player.equipo,
                ]
            }

            long teamGoals = expandedTeamPlayers.goles.sum()
            long teamGoalsObjective = expandedTeamPlayers.goles_minimos.sum()

            def teamBonusRatio = (teamGoals >= teamGoalsObjective)
                    ? 1
                    : (teamGoals/teamGoalsObjective)

            expandedTeamPlayers.collect { player ->
                def playerObjective = player.goles_minimos
                def goals = player.goles
                def playerBonusRatio = (goals >= playerObjective)
                        ? 1
                        : (goals/playerObjective)
                // oluna: Me gusta el orden de las operaciones, multiplicaciones y sumas primero!. hacer la division antes puede llevar a fallas de presición...
                def bonus = (player.bono * (teamBonusRatio + playerBonusRatio) / 2).round(2)

                player.sueldo_completo = player.sueldo + bonus
                player
            }
        }.flatten()
    }
}
