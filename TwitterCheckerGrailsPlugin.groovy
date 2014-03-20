import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import twitterChecker.*

class TwitterCheckerGrailsPlugin {
    def version = "0.3"
    def grailsVersion = "1.3.8 > *"
    def dependsOn = [quartz: "0.4.1 > *"]
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Alberto Vilches"
    def authorEmail = "vilches@gmail.com"
    def title = "Twitter checker Plugin (followers, timeline, mentions and RTs checker)"
    def description = '''
Uses the Twitter4J api (with OAuth) to periodically check (using Quartz) for new followers and unfollows, and trigger events to send emails/direct messages or whatever you want.
Checks your timeline, mentions to you and RTs of you, and show it in your site with a very simple TagLib.'''
    def documentation = "http://grails.org/plugin/twitter-checker"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
