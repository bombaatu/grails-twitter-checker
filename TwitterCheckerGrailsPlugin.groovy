import twitterChecker.*

class TwitterCheckerGrailsPlugin {
    def version = "0.3"
    def grailsVersion = "2.0 > *"
    def title = "Twitter checker Plugin (followers, timeline, mentions and RTs checker)"
    def description = '''\
Uses the Twitter4J api (with OAuth) to periodically check (using Quartz) for new followers and unfollows, and trigger events to send emails/direct messages or whatever you want.
Checks your timeline, mentions to you and RTs of you, and show it in your site with a very simple TagLib.'''
    def documentation = "http://grails.org/plugin/twitter-checker"
    def license = 'APACHE'
    def developers = [
        [name: 'Shawn Fike', email: 'shawnfike@hotmail.com'],
        [name: 'Alberto Vilches', email: 'vilches@gmail.com']
    ]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/bombaatu/grails-twitter-checker/issues']
    def scm = [ url: 'https://github.com/bombaatu/grails-twitter-checker']
}
