ant.mkdir(dir:"${basedir}/grails-app/controllers/twitterChecker")
ant.mkdir(dir:"${basedir}/grails-app/views/twitterChecker")
ant.mkdir(dir:"${basedir}/src/groovy/twitterChecker")

ant.copy(file:"${pluginBasedir}/src/templates/controllers/twitterChecker/TwitterCheckerController.groovy",todir:"${basedir}/grails-app/controllers/twitterChecker")
ant.copy(todir:"${basedir}/grails-app/views/twitterChecker") {
    fileset(dir:"${pluginBasedir}/src/templates/views/twitterChecker/")
}
ant.copy(file:"${pluginBasedir}/src/templates/groovy/twitterChecker/DefaultTwitterCheckerListener.groovy",todir:"${basedir}/src/groovy/twitterChecker")
