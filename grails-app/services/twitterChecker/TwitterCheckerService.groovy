/** ***********************************
 * Shawn Fike - twitter4j.http.* has been
 * migrated to twitter4j.auth.* in version
 * 2.2.0. Any usage of twitter4j.http has
 * been changed to twitter4j.auth.
 */

package twitterChecker

import org.springframework.beans.factory.InitializingBean

import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

class TwitterCheckerService implements InitializingBean {

    static transactional = false

    def grailsApplication

    @Delegate Twitter twitter = new TwitterFactory().getInstance()

    void afterPropertiesSet() {
        def conf = grailsApplication.config.twitterChecker
        def configured = conf.oauth.consumerKey && conf.oauth.consumerSecret
        if (configured) {
            twitter.setOAuthConsumer(conf.oauth.consumerKey, conf.oauth.consumerSecret)
        }
        else {
            log.error("OAuth configuration not defined in Config.groovy: twitterChecker.oauth.consumerKey or twitterChecker.oauth.consumerSecret missing")
        }

        configured = conf.token && conf.tokenSecret
        if (configured) {
            twitter.setOAuthAccessToken(new AccessToken(conf.token, conf.tokenSecret))
        }
        else {
            log.error("OAuth configuration not defined in Config.groovy: twitterChecker.token or twitterChecker.tokenSecret missing")
        }
    }

    def cachedMentions = []
    def cachedTimeline = []
    def cachedRts = []
}
