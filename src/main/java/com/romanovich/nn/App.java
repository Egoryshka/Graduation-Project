package com.romanovich.nn;


import com.romanovich.nn.ui.UserInterface;
import javafx.application.Application;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Application.launch(UserInterface.class);
    }

//    private static void geneticDemo() {
//        NetworkContext context = NetworkContext.buildGeneticContext(ImgResolution.W9_H12, 10, new int[]{},
//                0.7, ErrorFunctionType.EUCLID_ERROR, TrainerType.GENETIC, 40, 100000, 0.05d,
//                SelectionType.BEST_PART, 10, 0.01d, 10,
//                MutationType.GAUSS_MUTATION);
//    }
//
//    private static void backPropagationDemo() {
//        NetworkContext context = NetworkContext.buildBackPropagationContext(ImgResolution.W9_H12, 10, new int[]{},
//                0.8, ErrorFunctionType.EUCLID_ERROR, TrainerType.BACK_PROPAGATION, 40, 0.4, 0.2);
//    }
}
