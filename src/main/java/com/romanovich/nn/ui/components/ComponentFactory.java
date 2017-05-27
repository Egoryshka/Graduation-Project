package com.romanovich.nn.ui.components;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author romanovich
 * @since 26.05.2017
 */
public final class ComponentFactory {

    private ComponentFactory() {

    }

    public static ImageView getImageView(double width, double heigth) {
        final ImageView imageView = new ImageView();
        imageView.setFitWidth(width);
        imageView.setFitHeight(heigth);
        imageView.setStyle("-fx-border-color: black;");
        return imageView;
    }

    public static BorderedTitledPane getBorderedContainer(String title, Node child) {
        BorderedTitledPane pane = new BorderedTitledPane(title, child);
        pane.setTitleAlignment(Pos.TOP_LEFT);
        pane.setPadding(new Insets(8, 5, 5, 5));
        return pane;
    }

    public static ComboBox<String> getComboBox(List<String> comboItems, String selected) {
        ComboBox<String> combo = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList(comboItems);
        combo.setItems(items);
        if (StringUtils.isNotEmpty(selected) && comboItems.contains(selected)) {
            combo.setValue(selected);
        }
        return combo;
    }
}
