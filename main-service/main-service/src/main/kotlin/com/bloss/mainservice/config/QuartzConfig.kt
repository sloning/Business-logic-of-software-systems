package com.bloss.mainservice.config

import com.bloss.mainservice.util.CleanJob
import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class QuartzConfig(
    private val appProperties: AppProperties
) {
    @Bean
    fun jobDetail(): JobDetail {
        return JobBuilder.newJob().ofType(CleanJob::class.java)
            .storeDurably()
            .withIdentity("Clean job details")
            .withDescription("Invoke CleanService to hide old posts")
            .build()
    }

    @Bean
    fun trigger(job: JobDetail): Trigger {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity("Clean job trigger")
            .withDescription("Triggers clean job")
            .withSchedule(CronScheduleBuilder.cronSchedule(appProperties.schedule.scheduleToHidePosts))
            .build()
    }

    @Bean
    fun scheduler(trigger: Trigger, job: JobDetail, factory: SchedulerFactoryBean): Scheduler {
        val scheduler: Scheduler = factory.scheduler
        if (scheduler.checkExists(job.key)) scheduler.deleteJob(job.key)

        scheduler.scheduleJob(job, trigger)
        scheduler.start()
        return scheduler
    }
}