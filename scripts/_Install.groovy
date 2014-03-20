//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//


ant.mkdir(dir:"${basedir}/grails-app/controllers/twitterChecker")
ant.mkdir(dir:"${basedir}/grails-app/views/twitterChecker")
ant.mkdir(dir:"${basedir}/src/groovy/twitterChecker")

ant.copy(file:"${pluginBasedir}/src/templates/controllers/twitterChecker/TwitterCheckerController.groovy",todir:"${basedir}/grails-app/controllers/twitterChecker")
ant.copy(todir:"${basedir}/grails-app/views/twitterChecker") {
    fileset(dir:"${pluginBasedir}/src/templates/views/twitterChecker/")
}
ant.copy(file:"${pluginBasedir}/src/templates/groovy/twitterChecker/DefaultTwitterCheckerListener.groovy",todir:"${basedir}/src/groovy/twitterChecker")
