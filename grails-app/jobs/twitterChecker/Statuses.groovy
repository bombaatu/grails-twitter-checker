package twitterChecker

import java.util.zip.*
import org.codehaus.groovy.grails.commons.*

class Statuses {

    static def read(File file) {
        FileInputStream fos = new FileInputStream(file)
        ObjectInputStream oos = new ObjectInputStream(new GZIPInputStream(fos))
        List list = (List) oos.readObject()
        oos.close()
        return list
    }

    static def write(List list, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file)
        ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(fos))
        oos.writeObject(list)
        oos.close()
    }

    static def dispatchEvents(currentStatuses, fileType, listener, event) {
        if (!listener) return
        def currentStatusesIds = currentStatuses*.id
        File file = createFilename(fileType)

        boolean updateFile = false
        if (file.exists()) {
            def previousStatusesIds = read(file)
            def newStatusesIds = currentStatusesIds - previousStatusesIds
            if (newStatusesIds) {
                updateFile = true
                listener."$event"(currentStatuses.grep { it.id in newStatusesIds })
            }
        } else {
            // First time
            updateFile = true
        }

        if (updateFile) {
            write(currentStatusesIds, file)
        }
    }

    public static File createFilename(fileType) {
        return new File("${ConfigurationHolder.config.twitterChecker.storageFolder}/${ConfigurationHolder.config.twitterChecker.accountId}.${fileType}.bin")
    }

}
