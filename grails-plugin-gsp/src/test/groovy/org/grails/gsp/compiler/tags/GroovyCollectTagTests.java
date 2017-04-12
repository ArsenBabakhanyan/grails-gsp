package org.grails.gsp.compiler.tags;

import junit.framework.TestCase;
import org.grails.gsp.GroovyPage;
import org.grails.gsp.compiler.GroovyPageParser;
import org.grails.taglib.GrailsTagException;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author graemerocher
 */
public class GroovyCollectTagTests extends TestCase {

    private GroovyCollectTag tag = new GroovyCollectTag();
    private StringWriter sw = new StringWriter();

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Map context = new HashMap();
        context.put(GroovyPage.OUT, new PrintWriter(sw));
        GroovyPageParser parser=new GroovyPageParser("test", "test", "test", new ByteArrayInputStream(new byte[]{}));
        context.put(GroovyPageParser.class, parser);
        tag.init(context);
    }

    /**
     * Test method for {@link org.grails.gsp.compiler.tags.GroovyCollectTag#isKeepPrecedingWhiteSpace()}.
     */
    public void testIsKeepPrecedingWhiteSpace() {
        assertTrue(tag.isKeepPrecedingWhiteSpace());
    }

    /**
     * Test method for {@link org.grails.gsp.compiler.tags.GroovyCollectTag#isAllowPrecedingContent()}.
     */
    public void testIsAllowPrecedingContent() {
        assertTrue(tag.isAllowPrecedingContent());
    }

    /**
     * Test method for {@link org.grails.gsp.compiler.tags.GroovyCollectTag#doStartTag()}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testDoStartTag() {
        Map attrs = new HashMap();

        try {
            tag.doStartTag();
            fail("can't create this tag with no [in] and [expr] attributes");
        }
        catch (GrailsTagException e) {
            // expected
        }
        attrs.put("\"in\"", "myObj");
        attrs.put("\"expr\"", " ${ it.name }");
        tag.setAttributes(attrs);
        assertFalse(tag.attributes.isEmpty());
        tag.doStartTag();

        assertEquals("for( "+tag.getForeachRenamedIt()+" in evaluate('myObj.collect {it.name}', 1, it) { return myObj.collect {it.name} } ) {"+ System.getProperty("line.separator") + "changeItVariable(" + tag.getForeachRenamedIt() + ")" + System.getProperty("line.separator"),sw.toString());
    }
}
