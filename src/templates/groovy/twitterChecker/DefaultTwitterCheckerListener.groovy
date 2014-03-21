package twitterChecker

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import twitter4j.User

@Component("DefaultTwitterCheckerListenerBean")
class DefaultTwitterCheckerListener {

    @Autowired
    TwitterCheckerService twitterCheckerService

    static Log log = LogFactory.getLog(this)

    void onNewFollowers(ids) {
        for (id in ids) {
            User user = twitterCheckerService.showUser(id) // The showUser() method makes a real Twitter request to get the user info
            log.debug "New follower: @${user.screenName}"

//            twitterCheckerService.sendDirectMessage(id, "Thank you for following me!")
        }
    }

    void onUnfollows(ids) {
        for (id in ids) {
            User user = twitterCheckerService.showUser(id)
            log.debug  "Unfollow: @${user.screenName}"

//            twitterCheckerService.updateStatus("See you soon @${user.screenName}!")
        }
    }

    void onMentions(statuses) {
        for (status in statuses) {
            log.debug  "New mention: $status.text"
        }
    }

    void onRetweets(statuses) {
        for (status in statuses) {
            log.debug  "New RT: $status.text"
        }
    }
}
