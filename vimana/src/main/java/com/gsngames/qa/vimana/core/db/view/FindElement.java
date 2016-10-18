package com.gsngames.qa.vimana.core.db.view;

import org.openqa.selenium.By;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

public class FindElement {
	
	@Attribute(required = true)
    private String browser;
    
    @Attribute(required = false)
    private String intl;

    @Text(required = false)
    private String value;

    private By by;
    
    public String getIntl() {
		return intl;
	}

	public void setIntl(String intl) {
		this.intl = intl;
	}

	public By getBy() {
		return by;
	}

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void setBy(By by){
        this.by = by;
    }

}
