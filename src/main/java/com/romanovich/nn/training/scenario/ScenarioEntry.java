package com.romanovich.nn.training.scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author romanovich
 * @since 21.05.2017
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entry")
public class ScenarioEntry implements Serializable {

    @XmlElement
    private String example;
    @XmlElement
    private String expected;

    public String getExample() {
        return example;
    }

    public String getExpected() {
        return expected;
    }
}
