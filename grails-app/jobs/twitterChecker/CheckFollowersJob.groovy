package twitterChecker

import org.codehaus.groovy.grails.commons.*

class CheckFollowersJob {
    static concurrent = false
    static durability = true
    static volatility = false
    static triggers = {
        simple(name: 'CheckFollowersJob',
               repeatInterval: (ConfigurationHolder.config.twitterChecker.checkFollowersEvery?:15)*60*1000) // Every 15 minutes
    }

    TwitterCheckerService twitterCheckerService

    def execute() {
        def listener = ApplicationHolder.application.mainContext.getBean(ConfigurationHolder.config.twitterChecker.listenerBean?:"DefaultTwitterCheckerListenerBean")
         // Shawn Fike - followersIDs property changed to getFollowersIDs(-1l) to conform to updated twitterImpl class
        def currentFollowers = twitterCheckerService.getFollowersIDs(-1l).IDs as List
        File file = Statuses.createFilename("followers")

        boolean updateFollowersList = false
        if (file.exists()) {
            def previousFollowers = Statuses.read(file)
            def newFollowers = currentFollowers - previousFollowers
            if (newFollowers) {
                updateFollowersList = true
                listener.onNewFollowers(newFollowers)
            }

            def newUnfollows = previousFollowers - currentFollowers
            if (newUnfollows) {
                updateFollowersList = true
                listener.onUnfollows(newUnfollows)
            }
        } else {
            // First time
            updateFollowersList = true
        }

        if (updateFollowersList) {
            Statuses.write(currentFollowers, file)
        }
    }

}
