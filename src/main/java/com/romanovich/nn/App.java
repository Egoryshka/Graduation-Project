package com.romanovich.nn;


import com.romanovich.nn.network.NetworkContext;
import com.romanovich.nn.network.NeuralNetwork;
import com.romanovich.nn.training.error.ErrorFunctionType;
import com.romanovich.nn.training.scenario.ScenarioService;
import com.romanovich.nn.training.scenario.TrainingSet;
import com.romanovich.nn.training.strategy.TrainerType;
import com.romanovich.nn.training.strategy.TrainingStrategy;
import com.romanovich.nn.training.strategy.genetic.GeneticAlgorithm;
import com.romanovich.nn.training.strategy.genetic.operations.MutationType;
import com.romanovich.nn.training.strategy.genetic.operations.SelectionType;
import com.romanovich.nn.training.strategy.propagation.BackPropagation;
//import com.romanovich.nn.ui.UserInterface;
import com.romanovich.nn.utils.ImgResolution;
import com.romanovich.nn.utils.converter.ImageDataConverter;
//import javafx.application.Application;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
//        Application.launch(UserInterface.class);
//        backPropagationDemo();
//        geneticDemo();
    }

    private static void geneticDemo() {
        NetworkContext context = NetworkContext.buildGeneticContext(ImgResolution.W9_H12, 10, new int[]{},
                0.7, ErrorFunctionType.EUCLID_ERROR, TrainerType.GENETIC, 40, 100000, 0.05d,
                SelectionType.BEST_PART, 10, 0.01d, 10,
                MutationType.GAUSS_MUTATION);
        NeuralNetwork network = new NeuralNetwork(context.getResolution().getImageSize(),
                context.getOutputCount(), context.getHiddenLayers(), context.getActivationParam());
        TrainingStrategy trainer = new GeneticAlgorithm();
        TrainingSet trainingSet = ScenarioService.getInstance().loadTrainingScenario(context.getResolution());
        trainer.train(network, context, trainingSet);
        ImageDataConverter converter = new ImageDataConverter(context.getResolution());
        double[] input = converter.convert("D:\\dev\\Graduation Project\\TrainingSet\\Original\\0\\0AriaR.jpg");
        double[] result = network.proceed(input);
        System.out.println(context.getConverter().convertBack(result));
        StringBuilder out = new StringBuilder("[");
        for (double res : result) {
            out.append(String.valueOf(res)).append(", ");
        }
        out = new StringBuilder(out.substring(0, out.length() - 2) + "]");
        System.out.println(out.toString());
    }

    private static void backPropagationDemo() {
        NetworkContext context = NetworkContext.buildBackPropagationContext(ImgResolution.W9_H12, 10, new int[]{},
                0.8, ErrorFunctionType.EUCLID_ERROR, TrainerType.BACK_PROPAGATION, 40, 0.4, 0.2);
        NeuralNetwork network = new NeuralNetwork(context.getResolution().getImageSize(),
                context.getOutputCount(), context.getHiddenLayers(), context.getActivationParam());
        TrainingStrategy trainer = new BackPropagation();
        TrainingSet trainingSet = ScenarioService.getInstance().loadTrainingScenario(context.getResolution());
        trainer.train(network, context, trainingSet);

        String input;
        Scanner in = new Scanner(System.in);
        ImageDataConverter converter = new ImageDataConverter(context.getResolution());
        do {
            System.out.println("\nВведите путь к файлу:");
            input = in.nextLine();
            if (StringUtils.isEmpty(input)) {
                break;
            } else if (!(new File(input).exists())) {
                System.out.println("Incorrect path!");
                continue;
            }
            double[] inVector = converter.convert(input);
            double[] result = network.proceed(inVector);
            System.out.println(context.getConverter().convertBack(result));
            StringBuilder out = new StringBuilder("[");
            for (double res : result) {
                out.append(String.valueOf(res).substring(0, 4)).append(", ");
            }
            out = new StringBuilder(out.substring(0, out.length() - 2) + "]");
            System.out.println(out.toString());
        } while (StringUtils.isNoneEmpty(input));
    }
}
