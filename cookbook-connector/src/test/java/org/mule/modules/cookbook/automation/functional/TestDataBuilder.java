/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.functional;

import org.mule.tools.devkit.ctf.exceptions.XMLUtilsException;
import org.mule.tools.devkit.ctf.utils.XMLUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.stream.XMLStreamReader;
import java.util.Map;

public class TestDataBuilder {

    private static Map<String, Object> getSpringBean(String beanName) {
        ApplicationContext context = new ClassPathXmlApplicationContext("AutomationSpringBeans.xml");
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) context.getBean(beanName);
        ((ConfigurableApplicationContext) context).close();
        return testData;
    }

    private static String getFileName(String operation) {
        final String path = System.getProperty("user.dir") + "/src/test/resources/payloads.xml/IMuleCookBookService#" + operation.toLowerCase() + ".xml";
        return path;
    }

    public static Map<String, Object> createTestData() {
        return getSpringBean("createTestData");
    }

    public static Map<String, Object> deleteTestData() {
        return getSpringBean("deleteTestData");
    }

    public static Map<String, Object> getRecentlyAddedTestData() {
        return getSpringBean("getRecentlyAddedTestData");
    }

    public static Map<String, Object> getTestData() {
        return getSpringBean("getTestData");
    }

    public static Map<String, Object> queryPaginatedTestData() {
        return getSpringBean("queryPaginatedTestData");
    }

    public static Map<String, Object> updateTestData() {
        return getSpringBean("updateTestData");
    }

    public static XMLStreamReader getXMLStreamReaderForGet() throws XMLUtilsException {
        return XMLUtils.loadFile(getFileName("get"));
    }
}
