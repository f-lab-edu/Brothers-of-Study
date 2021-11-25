package kr.bos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class BosApplicationTest extends Specification {

    @Autowired
    private ApplicationContext context;

    def "load context"() {
        expect: "context will be loaded"
        context
    }
}
