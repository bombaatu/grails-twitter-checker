package twitterChecker

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class TwitterCheckerController {

    def twitterCheckerService

    def index() {
        def conf = grailsApplication.config.twitterChecker

        def configured = conf.oauth.consumerKey && conf.oauth.consumerSecret

        if (!configured) {
            render
"""<html><body><h1>Ops</h1>
            You need to config first you application.
            <ol><li>Create a new Twitter app (access type:read&write, type:client) in <a href="http://dev.twitter.com/apps/new" target="_blank">http://dev.twitter.com/apps/new</a><li>
                <li>Put the consumer key and consumer secret data in your Config.groovy as:</li>
            </ol>
            <pre>
            twitterChecker {
                oauth.consumerKey = ""
                oauth.consumerSecret = ""

            }
            </pre>
            </body></html>"""
            return
        }

        configured = conf.token && conf.tokenSecret
        if (!configured) {
            render view: "/twitterChecker/index"
        }
        else {
            render view: "/twitterChecker/demo"
        }
    }

    def auth() {
        def conf = grailsApplication.config.twitterChecker

        Twitter twitter = new TwitterFactory().getInstance()
        twitter.setOAuthConsumer(conf.oauth.consumerKey, conf.oauth.consumerSecret)

        RequestToken requestToken = twitter.getOAuthRequestToken()

        session.requestToken = requestToken

        redirect url: requestToken.getAuthorizationURL()
    }

    def addAccount() {

        RequestToken requestToken = session.requestToken
        if (!requestToken) {
            flash.error = "You need a new PIN first"
            redirect(action: 'index')
            return
        }

        session.removeAttribute('requestToken')

        Twitter twitter = new TwitterFactory().getInstance()
        twitter.setOAuthConsumer(conf.oauth.consumerKey, conf.oauth.consumerSecret)

        AccessToken accessToken
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, params.pin)
        }
        catch (TwitterException e) {
            flash.error = e.message
            redirect(action: 'index')
            return
        }

        //persist to the accessToken for future reference.
        render """
<html><body><h1>Well done!</h1>
Put this data in your Config.groovy
<pre>
twitterChecker {
    // Don't remove your own config oauth.consumerKey and other stuff...
    accountId = "${twitter.verifyCredentials().id}"
    token = "${accessToken.token}"
    tokenSecret = "${accessToken.tokenSecret}"
}
</pre>
</body></html>"""
    }

    def demo() {
    }
}
