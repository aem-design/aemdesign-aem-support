package specs.component.lists.eventlist

import org.apache.commons.io.IOUtils
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class EventListPublishFeedTestSpec extends ComponentSpec {

    String pathPage = "component/lists/event-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/eventlist"

    def setupSpec() {
        loginAsAdmin()
    }

    def "Event List: Default with ICS Feed Enabled"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Event List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#eventlist5"

        when: "I download the feed file"
        def downloadSHA1 = downloadContent("/content/aemdesign-showcase/au/en/component/lists/event-list/jcr:content/article/par/contentblock5/par/eventlist.test.ics")
        def feedContent = IOUtils.toString(new InputStreamReader(downloadSHA1))

        then: "The page should have begin calendar tag"
        assert feedContent.contains("BEGIN:VCALENDAR")

        and: "The page should have list name tag"
        assert feedContent.contains("X-WR-CALNAME:Event List")


    }


}
