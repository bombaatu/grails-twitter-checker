package twitterChecker

import org.codehaus.groovy.grails.commons.*

class CheckTimelineJob  {
    static concurrent = false
    static durability = true
    static volatility = false
    static triggers = {
        simple(name: 'CheckTimelineJob',
                repeatInterval: (ConfigurationHolder.config.twitterChecker.checkTimelineEvery?:15)*60*1000) // Every 15 minutes
    }

    TwitterCheckerService twitterCheckerService

    def execute() {

        def timeline = twitterCheckerService.userTimeline
        twitterCheckerService.cachedTimeline = timeline // Store timeline in the cache for Taglib
    }

}
