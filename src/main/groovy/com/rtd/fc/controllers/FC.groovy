package com.rtd.fc.controllers

import com.rtd.fc.services.PayrollService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/fc")
class FC {
    @Autowired
    PayrollService payrollService

    //@GetMapping('/salaries', produces = 'application/json')
    @GetMapping('/salaries')
    List<Map> salaries(@RequestBody Map payrol) {
        payrollService.calculateSalaries(payrol.jugadores, payrol.metas)
    }
}