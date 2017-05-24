package com.romanovich.nn.training.scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author romanovich
 * @since 21.05.2017
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "scenario")
public class Scenario implements Serializable {

    @XmlElement(name = "entry")
    private List<ScenarioEntry> entries;

    public List<ScenarioEntry> getEntries() {
        return entries;
    }
}
