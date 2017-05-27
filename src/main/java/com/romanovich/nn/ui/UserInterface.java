package com.romanovich.nn.ui;

import com.romanovich.nn.business.ImageResolver;
import com.romanovich.nn.layer.neuron.activation.ActivationType;
import com.romanovich.nn.network.NetworkContext;
import com.romanovich.nn.training.error.ErrorFunctionType;
import com.romanovich.nn.training.strategy.TrainerType;
import com.romanovich.nn.training.strategy.genetic.operations.MutationType;
import com.romanovich.nn.training.strategy.genetic.operations.SelectionType;
import com.romanovich.nn.ui.components.BorderedTitledPane;
import com.romanovich.nn.ui.components.ComponentFactory;
import com.romanovich.nn.ui.settings.UISettings;
import com.romanovich.nn.utils.ImgResolution;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author romanovich
 * @since 25.05.2017
 */
public class UserInterface extends Application {

    private String selectedImage = "";
    ComboBox<String> imageSize;
    TextField hiddenLayers;
    TextField networkOutput;
    ComboBox<String> neuronFunc;
    TextField activationParam;

    ComboBox<String> errorFunc;
    TrainerType trainerType;

    VBox trainerSection;

    TextField erasField;
    TextField trainingRateField;
    TextField regularizationField;

    TextField populationField;
    TextField generationsField;
    ComboBox<String> selectionField;
    TextField crossoverCount;
    ComboBox<String> mutationField;
    TextField mutationProb;
    TextField mutationCount;

    Button createNetwork;
    Button convert;
    Label result;

    TextArea log;

    ImageResolver resolver;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Neural network");
        Image icon = new Image(getClass().getResource("/ui/network.png").toURI().toString());
        primaryStage.getIcons().add(icon);
        drawForm(primaryStage);
    }

    private void drawForm(Stage stage) throws MalformedURLException {
        final HBox rootBox = new HBox(15);
        rootBox.setStyle(UISettings.defaultBackground);

        rootBox.getChildren().addAll(initNetworkParamsSide(), initLogSide(), initImageSide());

        Scene scene = new Scene(rootBox, UISettings.windowWidth, UISettings.windowHeight);
        stage.setScene(scene);
        stage.show();
    }

    private void addLoadImageButtonHandler(Node target, ImageView view, int clickCount) {
        target.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == clickCount) {
                handleImageLoading(view);
            }
        });
    }

    private void handleImageLoading(ImageView view) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:/dev/Graduation Project/TrainingSet/Original"));
        fileChooser.setTitle("Select Image");
        String url = "";
        try {
            File image = fileChooser.showOpenDialog(null);
            if (image != null) {
                selectedImage = image.getAbsolutePath();
                url = image.toURI().toURL().toString();
            }
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException arisen!!!");
            url = "";
        }
        if (StringUtils.isNoneEmpty(url)) {
            Image newImage = new Image(url);
            view.setImage(newImage);
        }
    }

    private VBox initImageSide() {
        VBox imageSide = new VBox(25);
        imageSide.setPadding(UISettings.sectorInsets);

        final ImageView imageView = ComponentFactory.getImageView(UISettings.imageWidth, UISettings.imageHeight);
        addLoadImageButtonHandler(imageView, imageView, 2);

        Button loadImageButton = new Button("Load Image");
        addLoadImageButtonHandler(loadImageButton, imageView, 1);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().add(loadImageButton);

        VBox box = new VBox(5);
        box.getChildren().addAll(imageView, buttonBox);

        BorderedTitledPane imageHolder = ComponentFactory.getBorderedContainer("Image Holder", box);
        imageSide.getChildren().addAll(imageHolder, initSolverSection());

        return imageSide;
    }

    private BorderedTitledPane initSolverSection() {
        Label label = new Label("Result: ");
        label.setFont((new Font(16)));

        result = new Label();
        result.setFont((new Font(16)));
        result.setAlignment(Pos.CENTER_RIGHT);

        HBox resultBox = new HBox(15);
        resultBox.getChildren().addAll(label, result);

        convert = new Button("Resolve");
        convert.setFont(new Font(16));
        convert.setPrefWidth(UISettings.imageWidth);
        convert.setDisable(true);
        convert.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == 1) {
                if (StringUtils.isNotEmpty(selectedImage)) {
                    int value = resolver.proceed(selectedImage);
                    if (value != -1) {
                        result.setText(String.valueOf(value));
                    } else {
                        result.setText("Network error");
                    }
                } else {
                    result.setText(String.valueOf("Sample not selected"));
                }
            }
        });

        VBox box = new VBox(10);
        box.getChildren().addAll(resultBox, convert);

        return ComponentFactory.getBorderedContainer("Resolver", box);
    }


    private VBox initNetworkParamsSide() {
        VBox root = new VBox(10);
        root.setPadding(UISettings.leftSectorInsets);
        Label label = new Label("Resolver parameters");
        root.getChildren().add(label);

        BorderedTitledPane networkParams = ComponentFactory.getBorderedContainer("Network parameters", null);
        networkParams.getChildren().add(initNetworkParamsSection());

        BorderedTitledPane trainerParams = ComponentFactory.getBorderedContainer("Trainer parameters", null);
        trainerParams.getChildren().add(initTrainerParameters());

        createNetwork = initCreateButton();
        createNetwork.setPrefWidth(UISettings.createButtonWidth);

        root.getChildren().addAll(networkParams, trainerParams, createNetwork);
        return root;
    }

    private VBox initNetworkParamsSection() {
        VBox section = new VBox(4);

        imageSize = ComponentFactory.getComboBox(ImgResolution.getDescriptions(), ImgResolution.W9_H12.getDescription());
        HBox inputLine = initComboBoxLine("Image size:", imageSize);
        hiddenLayers = new TextField();
        hiddenLayers.setTooltip(new Tooltip("Value format: \"n1,n2,...,nM\". For Example \"4,6,3\""));
        HBox hiddenLine = initTextFieldLine("Hidden layers:", hiddenLayers, true);
        networkOutput = new TextField("10");
        HBox outputLine = initTextFieldLine("Output vector size:", networkOutput, false);
        neuronFunc = ComponentFactory.getComboBox(ActivationType.getFunctions(), ActivationType.SIGMOID.getFunction());
        HBox neuronFuncLine = initComboBoxLine("Activation function:", neuronFunc);
        activationParam = new TextField("0.5");
        HBox activationLine = initTextFieldLine("Activation param:", activationParam, true);

        section.getChildren().addAll(inputLine, hiddenLine, outputLine, neuronFuncLine, activationLine);
        return section;
    }

    private VBox initTrainerParameters() {
        trainerSection = new VBox(6);

        errorFunc = ComponentFactory.getComboBox(ErrorFunctionType.getFunctions(), ErrorFunctionType.EUCLID_ERROR.getFunction());
        HBox errorFunction = initComboBoxLine("Error function type", errorFunc);
        BorderedTitledPane trainerTypeToggle = initTrainersGroup("Trainer type", TrainerType.values());

        VBox trainer = initTrainerSection();

        trainerSection.getChildren().addAll(errorFunction, trainerTypeToggle, trainer);
        return trainerSection;
    }

    private BorderedTitledPane initTrainersGroup(String label, TrainerType[] items) {
        ToggleGroup group = new ToggleGroup();
        List<RadioButton> buttons = new ArrayList<>();
        for (TrainerType item : items) {
            RadioButton button = new RadioButton(item.getTrainer());
            button.setUserData(item);
            button.setToggleGroup(group);
            buttons.add(button);
        }
        buttons.get(0).setSelected(true);
        trainerType = (TrainerType) buttons.get(0).getUserData();
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                trainerType = (TrainerType) group.getSelectedToggle().getUserData();
                trainerSection.getChildren().remove(trainerSection.getChildren().size() - 1);
                trainerSection.getChildren().add(initTrainerSection());
            }
        });
        HBox hBox = new HBox(15);
        hBox.getChildren().addAll(buttons);

        return ComponentFactory.getBorderedContainer(label, hBox);
    }

    private VBox initTrainerSection() {
        VBox section = new VBox(4);
        if (TrainerType.BACK_PROPAGATION.equals(trainerType)) {
            erasField = new TextField("10");
            HBox erasLine = initTextFieldLine("Training eras count:", erasField, true);
            trainingRateField = new TextField("0.5");
            HBox rateLine = initTextFieldLine("Training rate:", trainingRateField, true);
            regularizationField = new TextField("0.5");
            HBox regularization = initTextFieldLine("Regularization amount:", regularizationField, true);

            section.getChildren().addAll(erasLine, rateLine, regularization);
        } else {
            populationField = new TextField("20");
            generationsField = new TextField("40");
            selectionField = ComponentFactory.getComboBox(SelectionType.getSelections(), SelectionType.ROULETTE_WHEEL.getSelection());
            crossoverCount = new TextField("4");
            mutationField = ComponentFactory.getComboBox(MutationType.getMutations(), MutationType.GAUSS_MUTATION.getMutation());
            mutationProb = new TextField("0.04");
            mutationCount = new TextField("5");

            HBox populationLine = initTextFieldLine("Population size:", populationField, true);
            HBox generationLine = initTextFieldLine("Generations count:", generationsField, true);
            HBox selectionLine = initComboBoxLine("Selection type:", selectionField);
            HBox crossoverLine = initTextFieldLine("Crossover points count:", crossoverCount, true);
            HBox mutationTypeLine = initComboBoxLine("Mutation type:", mutationField);
            HBox mutationProbLine = initTextFieldLine("Mutation probability:", mutationProb, true);
            HBox mutationCountLine = initTextFieldLine("Mutations count:", mutationCount, true);

            section.getChildren().addAll(populationLine, generationLine, selectionLine, crossoverLine,
                    mutationTypeLine, mutationProbLine, mutationCountLine);
        }
        return section;
    }

    private Button initCreateButton() {
        Button button = new Button("Create and train network");
        button.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == 1) {
                NetworkContext context = buildContext();
                resolver = new ImageResolver(context);
                String trainResult = resolver.trainNetwork();
                log.setText(trainResult);
                convert.setDisable(false);
            }
        });
        return button;
    }


    private VBox initLogSide() {
        VBox root = new VBox(10);
        root.setPadding(UISettings.sectorInsets);

        log = new TextArea();
        log.setPrefSize(UISettings.logWidth, UISettings.logHeight);
        log.setWrapText(true);
        log.setEditable(false);

        BorderedTitledPane logSection = ComponentFactory.getBorderedContainer("ApplicationLog", log);

        root.getChildren().add(logSection);
        return root;
    }


    private NetworkContext buildContext() {
        int[] hidden;
        if (StringUtils.isNotEmpty(hiddenLayers.getText())) {
            String[] hiddenStr = hiddenLayers.getText().split(",");
            hidden = new int[hiddenStr.length];
            for (int i = 0; i < hiddenStr.length; i++) {
                hidden[i] = Integer.valueOf(hiddenStr[i]);
            }
        } else {
            hidden = new int[]{};
        }
        if (TrainerType.BACK_PROPAGATION.equals(trainerType)) {
            return NetworkContext.buildBackPropagationContext(ImgResolution.findByDescription(imageSize.getValue()),
                    10, hidden, Double.valueOf(activationParam.getText()),
                    ErrorFunctionType.find(errorFunc.getValue()), TrainerType.BACK_PROPAGATION, Integer.valueOf(erasField.getText()),
                    Double.valueOf(trainingRateField.getText()), Double.valueOf(regularizationField.getText()));
        } else {
            return NetworkContext.buildGeneticContext(ImgResolution.findByDescription(imageSize.getValue()),
                    10, hidden, Double.valueOf(activationParam.getText()), ErrorFunctionType.find(errorFunc.getValue()),
                    TrainerType.GENETIC, Integer.valueOf(populationField.getText()),
                    Integer.valueOf(generationsField.getText()), 0.05, SelectionType.find(selectionField.getValue()),
                    Integer.valueOf(crossoverCount.getText()), Double.valueOf(mutationProb.getText()),
                    Integer.valueOf(mutationCount.getText()), MutationType.find(mutationField.getValue()));
        }
    }

    private HBox initComboBoxLine(String label, ComboBox<String> field) {
        HBox inputLine = new HBox(UISettings.lineSpacing);
        inputLine.setAlignment(Pos.CENTER);
        Label inputLabel = new Label(label);
        inputLabel.setPrefWidth(UISettings.labelWidth);
        field.setPrefWidth(UISettings.fieldWidth);
        inputLine.getChildren().addAll(inputLabel, field);
        return inputLine;
    }

    private HBox initTextFieldLine(String label, TextField field, boolean editable) {
        HBox outputLine = new HBox(UISettings.lineSpacing);
        outputLine.setAlignment(Pos.CENTER);
        Label outputLabel = new Label(label);
        outputLabel.setPrefWidth(UISettings.labelWidth);
        field.setPrefWidth(UISettings.fieldWidth);
        field.setEditable(editable);
        outputLine.getChildren().addAll(outputLabel, field);
        return outputLine;
    }
}