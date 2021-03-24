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

    def calculateSalaries(List<Map> players, Map categoryObjectives = null) {
        if ( categoryObjectives == null ) {
            categoryObjectives = defaultCategoryObjectives
        }

        def teamGroups = players.groupBy { it.equipo }

        teamGroups.collect {team, teamPlayers ->

            def expandedTeamPlayers = expandTeamPlayersList( team, teamPlayers, categoryObjectives)

            long teamGoals = expandedTeamPlayers.goles.sum()
            long teamGoalsObjective = expandedTeamPlayers.goles_minimos.sum()

            def teamBonusRatio = scoreRatio( teamGoals, teamGoalsObjective )

            expandedTeamPlayers.collect { player ->
                def playerObjective = player.goles_minimos
                def goals = player.goles
                def playerBonusRatio = scoreRatio( goals, playerObjective )

                def bonus = calcBonus(player.bono, teamBonusRatio, playerBonusRatio)

                player.sueldo_completo = player.sueldo + bonus
                player
            }
        }.flatten()
    }

    /*
     * Expand the player list to the required goals scored
     */
    private List<Map> expandTeamPlayersList(String team, List<Map> teamPlayers, Map categoryObjectives ) {
        Map teamCategoryObjectives = categoryObjectives[team] ?: categoryObjectives?.default ?: [:]

        teamPlayers.collect { player ->
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
    }

    /*
     * calculate the score ration against the target
     */
    private def scoreRatio(scored, target) {
        (scored >= target)
                ? 1
                : (scored/target)
    }

    /*
     * Calculate the Bonus of the player
     */
    private def calcBonus(bonus, teamBonusRatio, playerBonusRatio) {
        // oluna: Me gusta el orden de las operaciones, multiplicaciones y sumas primero!. hacer la division antes puede llevar a fallas de presición...
        (bonus * (teamBonusRatio + playerBonusRatio) / 2).round(2)
    }
}
