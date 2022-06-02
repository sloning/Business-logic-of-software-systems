package com.bloss.mainservice.delegate

import com.bloss.mainservice.service.CleanService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.Expression
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("cleanByCreationDateDelegate")
class CleanByCreationDateDelegate(private val cleanService: CleanService) : JavaDelegate {
    lateinit var timeToHideByCreationDate: Expression

    override fun execute(execution: DelegateExecution) {
        val time = (timeToHideByCreationDate.getValue(execution) as String).toLong()
        cleanService.hidePostsByCreationDate(time)
    }
}