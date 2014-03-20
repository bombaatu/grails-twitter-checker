package twitterChecker

import org.codehaus.groovy.grails.commons.*

class CheckRetweetsJob {
    static concurrent = false
    static durability = true
    static volatility = false
    static triggers = {
        simple(name: 'CheckRetweetsJob',
                repeatInterval: (ConfigurationHolder.config.twitterChecker.checkRetweetsEvery?:15)*60*1000) // Every 15 minutes
    }

    TwitterCheckerService twitterCheckerService

    def execute() {

        def rts = twitterCheckerService.retweetsOfMe
        twitterCheckerService.cachedRts = rts // Store rts in the cache for Taglib

        def listener = ApplicationHolder.application.mainContext.getBean(ConfigurationHolder.config.twitterChecker.listenerBean?:"DefaultTwitterCheckerListenerBean")

        Statuses.dispatchEvents(rts, "rts", listener, "onRetweets")
    }

}
