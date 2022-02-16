package com.epam.finalproject.web.taghandler;

import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ColontitleHandler  extends TagSupport {

    private static final Logger log = Logger.getLogger(ColontitleHandler.class);

    @Override
    public int doStartTag()  {
        try {
            pageContext.getOut().write("<footer>");
            pageContext.getOut().write("<p class='text-center' style='position:fixed;\n" +
                    "   left:0px;\n" +
                    "   bottom:0px;\n" +
                    "   height:30px;\n" +
                    "   width:100%;' >");
            pageContext.getOut().write("By Platonov");
            pageContext.getOut().write("</p>");
            pageContext.getOut().write("</footer>");
        } catch(IOException ioe) {
            log.error(ioe);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag(){
        return SKIP_BODY;
    }

}
