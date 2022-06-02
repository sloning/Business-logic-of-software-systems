package com.bloss.mainservice.delegate

import com.bloss.mainservice.service.CleanService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.Expression
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("cleanByLastWatchedDateDelegate")
class CleanByLastWatchedDateDelegate(private val cleanService: CleanService) : JavaDelegate {
    lateinit var timeToHideByLastWatchedDate: Expression

    override fun execute(execution: DelegateExecution) {
        val time = (timeToHideByLastWatchedDate.getValue(execution) as String).toLong()
        cleanService.hidePostsByLastWatchedDate(time)
    }
}