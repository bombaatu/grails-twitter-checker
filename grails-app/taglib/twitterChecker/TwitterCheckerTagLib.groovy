package twitterChecker

class TwitterCheckerTagLib {

    static namespace = 'twitterChecker'

    def twitterCheckerService

    def timeline = { attrs, body ->
        Integer max = getMax(attrs.max, twitterCheckerService.cachedTimeline)
        out << g.render(template: '/twitterChecker/twitFromMe',
                model: [statuses: twitterCheckerService.cachedTimeline[0..<max]])
    }

    def eachTimeline = { attrs, body ->
        Integer max = getMax(attrs.max, twitterCheckerService.cachedTimeline)
        twitterCheckerService.cachedTimeline[0..<max].each{
            out << body(it)
        }
    }

    def mentions = { attrs, body ->
        Integer max = getMax(attrs.max, twitterCheckerService.cachedMentions)
        out << g.render(template: '/twitterChecker/twitFromOther',
                model: [statuses: twitterCheckerService.cachedMentions[0..<max]])
    }

    def eachMention = { attrs, body ->
        Integer max = getMax(attrs.max, twitterCheckerService.cachedMentions)
        twitterCheckerService.cachedMentions[0..<max].each {
            out << body(it)
        }
    }

    def rts = { attrs, body ->
        Integer max = getMax(attrs.max, twitterCheckerService.cachedRts)
        out << g.render(template: '/twitterChecker/twitFromOther',
                model: [statuses: twitterCheckerService.cachedRts[0..<max]])
    }

    def eachRt = { attrs, body ->
        Integer max = getMax(attrs.max, twitterCheckerService.cachedRts)
        twitterCheckerService.cachedRts[0..<max].each {
            out << body(it)
        }
    }

    def parseLinks = { attrs, body ->
        // Code taken from twitter-panel grails plugin
        def text = body()
        text = text
                // Replace links in text with real links
                .replaceAll(/(http[^\s]*)/) {fullMatch, url -> "<a class='user' href='${url}' target='_blank'>${url}</a>" }
                // replace username references with links to username
                .replaceAll(/@([^\s:]*)/) {fullMatch, name -> "<a class='link' href='http://twitter.com/${name}' target='_blank'>@${name}</a>" }
                // replace hash tags with links to twitter search for that tag
                .replaceAll(/#([^\s]*)/) {fullMatch, name -> "<a class='hashtag' href='http://search.twitter.com/search?q=%23${name}' target='_blank'>#${name}</a>" }

        out << text
    }

    private Integer getMax(def userMax, Collection collectionMax) {
        Integer fixedUserMax = userMax?.isInteger()?userMax as Integer: Integer.MAX_VALUE
        Math.min(collectionMax.size(), fixedUserMax)
    }
}
