/** ***********************************
 * Shawn Fike - twitter4j.http.* has been
 * migrated to twitter4j.auth.* in version
 * 2.2.0. Any usage of twitter4j.http has
 * been changed to twitter4j.auth.
 */

package twitterChecker

import org.apache.juli.logging.*
import org.codehaus.groovy.grails.commons.*
import org.springframework.beans.factory.*
import twitter4j.*
import twitter4j.auth.*

class TwitterCheckerService implements InitializingBean {

    static scope = 'singleton'
    static transactional = false
    static Log log = LogFactory.getLog(TwitterCheckerService.class.getName())

    @Delegate Twitter twitter = new TwitterFactory().getInstance()

    void afterPropertiesSet() {
        configureOauth()
    }

    private def configureOauth() {
        def conf = ConfigurationHolder.config.twitterChecker
        def configured = conf.oauth.consumerKey && conf.oauth.consumerSecret
        if (configured)
            twitter.setOAuthConsumer(conf.oauth.consumerKey, conf.oauth.consumerSecret)
        else
            log.error("OAuth configuration not defined in Config.groovy: twitterChecker.oauth.consumerKey or twitterChecker.oauth.consumerSecret missing")

        configured = conf.token && conf.tokenSecret
        if (configured)
            twitter.setOAuthAccessToken(new AccessToken(conf.token, conf.tokenSecret))
        else
            log.error("OAuth configuration not defined in Config.groovy: twitterChecker.token or twitterChecker.tokenSecret missing")
    }

    def cachedMentions = []
    def cachedTimeline = []
    def cachedRts = []
}
