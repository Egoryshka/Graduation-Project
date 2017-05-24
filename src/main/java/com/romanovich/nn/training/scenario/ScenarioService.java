package com.romanovich.nn.training.scenario;

import com.romanovich.nn.utils.ImgResolution;
import com.romanovich.nn.utils.converter.ImageDataConverter;
import com.romanovich.nn.utils.converter.NumberConverter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author romanovich
 * @since 22.05.2017
 */
public final class ScenarioService {

    private static ScenarioService instance;

    private ScenarioService() {

    }

    public static ScenarioService getInstance() {
        if (instance == null) {
            instance = new ScenarioService();
        }
        return instance;
    }

    public TrainingSet loadTrainingScenario(ImgResolution resolution) {
        String scenarioName = getScenarioName(resolution);
        InputStream scenarioStream = getClass().getClassLoader().getResourceAsStream(scenarioName);
        if (scenarioStream != null) {
            Scenario scenario = loadFromXML(scenarioStream);
            return convertScenario(scenario, resolution);
        }
        return new TrainingSet(new ArrayList<>());
    }

    private Scenario loadFromXML(InputStream scenarioResource) {
        try {
            JAXBContext context = JAXBContext.newInstance(Scenario.class, ScenarioEntry.class);
            return (Scenario) context.createUnmarshaller().unmarshal(scenarioResource);
        } catch (JAXBException ex) {
            System.out.println("JAXB Exception: " + ex);
            return new Scenario();
        }
    }

    private TrainingSet convertScenario(Scenario scenario, ImgResolution resolution) {
        List<TrainingEntry> entries = new ArrayList<>();
        ImageDataConverter converter = new ImageDataConverter(resolution);
        for (ScenarioEntry example : scenario.getEntries()) {
            double[] input = converter.convert(getClass().getClassLoader().getResourceAsStream(example.getExample()));
            double[] expected = NumberConverter.convert(example.getExpected());
            entries.add(new TrainingEntry(input, expected));
        }
        Collections.shuffle(entries);
        return new TrainingSet(entries);
    }

    private String getScenarioName(ImgResolution resolution) {
        switch (resolution) {
            case W12_H16:
                return "scenario12x16.xml";
            case W10_H16:
                return "scenario10x16.xml";
            case W9_H12:
                return "scenario9x12.xml";
            case W4_H6:
                return "scenario4x6.xml";
            case W6_H8:
            default:
                return "scenario6x8.xml";
        }
    }
}
