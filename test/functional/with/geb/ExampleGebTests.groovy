package with.geb

import geb.junit4.GebReportingTest
import org.junit.Test
import geb.Page

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import geb.Module
import static groovy.util.GroovyTestCase.assertEquals

/**
 * Created by IntelliJ IDEA.
 * User: ssmithstone
 * Date: 09/12/2011
 * Time: 06:01
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4)
class ExampleGebTests extends GebReportingTest {
    
    @Test
    public void testWeHaveSmokeInBrowser(){
    
        browser.to(IndexPage)
        
        assertEquals("Welcome To Grails".toLowerCase() , browser.page.title.toLowerCase())
    }
}


class IndexPage extends Page {
 
    static url = "/with-geb/"
}

class GoogleHomePage extends Page {
    static url = "http://google.com/ncr"
    static at = { title == "Google" }
    static content = {
        search { module GoogleSearchModule }
    }
}

class GoogleSearchModule extends Module {
    static content = {
        field { $("input", name: "q") }
        button(to: GoogleResultsPage) { btnG() }
    }

    void forTerm(term) {
        field.value term
        waitFor { button.displayed }
        button.click()
    }
}

class GoogleResultsPage extends Page {
    static at = { waitFor { title.endsWith("Google Search") } }
    static content = {
        search { module GoogleSearchModule }
        results { $("li.g") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a.l") }
        firstResultLink { resultLink(0) }
    }
}
