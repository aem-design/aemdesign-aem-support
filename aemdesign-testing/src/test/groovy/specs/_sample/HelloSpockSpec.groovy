package specs._sample

import org.junit.Ignore
import spock.lang.IgnoreIf
import spock.lang.Specification

@Ignore
@IgnoreIf({ System.properties.getProperty("geb.env") != "sample" })
class HelloSpockSpec extends Specification {
    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }
}
