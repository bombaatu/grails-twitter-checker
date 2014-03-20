package twitterChecker

import org.codehaus.groovy.grails.commons.*

class CheckMentionsJob  {
    static concurrent = false
    static durability = true
    static volatility = false
    static triggers = {
        simple(name: 'CheckMentionsJob',
               repeatInterval: (ConfigurationHolder.config.twitterChecker.checkMentionsEvery?:15)*60*1000) // Every 15 minutes
    }

    TwitterCheckerService twitterCheckerService

    def execute() {

        def mentions = twitterCheckerService.mentionsTimeline
        twitterCheckerService.cachedMentions = mentions // Store mentions in the cache for Taglib

        def listener = ApplicationHolder.application.mainContext.getBean(ConfigurationHolder.config.twitterChecker.listenerBean?:"DefaultTwitterCheckerListenerBean")

        Statuses.dispatchEvents(mentions, "mentions", listener, "onMentions")
    }

}
