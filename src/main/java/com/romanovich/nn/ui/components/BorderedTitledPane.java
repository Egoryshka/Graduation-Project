package com.romanovich.nn.ui.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javax.swing.text.html.StyleSheet;


/**
 * @author romanovich
 * @since 26.05.2017
 */
public class BorderedTitledPane extends StackPane {

    private static final String customTitle = "-fx-translate-x: 8; -fx-translate-y: -15; -fx-padding: 0 0 0 4;" +
            " -fx-background-color: #f3f3f3;";
    private static final String customBorder = "-fx-content-display: top; -fx-border-insets: 2 0 0 0;" +
            " -fx-border-color: black; -fx-border-width: 1;";

    private StringProperty title = new SimpleStringProperty();
    private ObjectProperty<Node> graphic = new SimpleObjectProperty<>();
    private ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private ObjectProperty<Pos>  titleAlignment = new SimpleObjectProperty<>();
    // todo other than TOP_LEFT other alignments aren't really supported correctly, due to translation fudge for indentation of the title label in css => best to implement layoutChildren and handle layout there.
    // todo work out how to make content the default node for fxml so you don't need to write a <content></content> tag.

    public BorderedTitledPane() {
        this("", null);
    }

    public BorderedTitledPane(String titleString, Node contentNode) {
        StyleSheet styleSheet = new StyleSheet();

        final Label titleLabel = new Label();
        titleLabel.textProperty().bind(Bindings.concat(title, " "));
        titleLabel.setStyle(customTitle);
        titleLabel.graphicProperty().bind(graphic);

        titleAlignment.addListener(observable -> StackPane.setAlignment(titleLabel, titleAlignment.get()));

        final StackPane contentPane = new StackPane();

        setStyle(customBorder);
        getChildren().addAll(titleLabel, contentPane);

        content.addListener(observable -> {
            if (content.get() == null) {
                contentPane.getChildren().clear();
            } else {
                contentPane.getChildren().setAll(content.get());
            }
        });

        titleAlignment.set(Pos.TOP_LEFT);
        this.title.set(titleString);
        this.content.set(contentNode);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty getTitleStringProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public Pos getTitleAlignment() {
        return titleAlignment.get();
    }

    public ObjectProperty<Pos> titleAlignmentProperty() {
        return titleAlignment;
    }

    public void setTitleAlignment(Pos titleAlignment) {
        this.titleAlignment.set(titleAlignment);
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public Node getGraphic() {
        return graphic.get();
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphic;
    }

    public void setGraphic(Node graphic) {
        this.graphic.set(graphic);
    }
}
