package org.atola.internal.suppressUntil.config;

import com.intellij.openapi.util.Comparing;
import com.intellij.ui.IdeBorderFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SuppressUntilConfigurationForm {

    private JCheckBox enable;
    private JPanel myMainPanel;
    private JTextField annotationClassName;
    private JTextField currentProductVesrion;
    private JTextField versionPropName;
    private JTextField namesPropName;

    private SuppressUntilConfigStorage suppressUntilConfigStorage;

    public SuppressUntilConfigurationForm() {
    }

    @NotNull
    public JComponent createPanel(@NotNull SuppressUntilConfigStorage suppressUntilConfigStorage) {
        this.suppressUntilConfigStorage = suppressUntilConfigStorage;

        myMainPanel.setBorder(IdeBorderFactory.createTitledBorder("Detekt Settings"));

        enable.addChangeListener(changeEvent -> {
            boolean enabled = enable.isSelected();
            annotationClassName.setEditable(enabled);
            currentProductVesrion.setEditable(enabled);
            versionPropName.setEditable(enabled);
            namesPropName.setEditable(enabled);
        });

        return myMainPanel;
    }

    public void apply() {
        suppressUntilConfigStorage.setEnable(enable.isSelected());
        suppressUntilConfigStorage.setProductVersion(currentProductVesrion.getText());
        suppressUntilConfigStorage.setAnnotationFqName(annotationClassName.getText());
        suppressUntilConfigStorage.setAnnotationVersionPropName(versionPropName.getText());
        suppressUntilConfigStorage.setAnnotationNamesPropName(namesPropName.getText());

        suppressUntilConfigStorage.UpdatePluginConfiguration();
    }

    public void reset() {
        enable.setSelected(suppressUntilConfigStorage.getEnable());
        currentProductVesrion.setText(suppressUntilConfigStorage.getProductVersion());
        annotationClassName.setText(suppressUntilConfigStorage.getAnnotationFqName());
        versionPropName.setText(suppressUntilConfigStorage.getAnnotationVersionPropName());
        namesPropName.setText(suppressUntilConfigStorage.getAnnotationNamesPropName());
    }

    public boolean isModified() {
        return !Comparing.equal(suppressUntilConfigStorage.getEnable(), enable.isSelected()) ||
            !Comparing.equal(currentProductVesrion, suppressUntilConfigStorage.getProductVersion()) ||
            !Comparing.equal(annotationClassName, suppressUntilConfigStorage.getAnnotationFqName()) ||
            !Comparing.equal(versionPropName, suppressUntilConfigStorage.getAnnotationVersionPropName()) ||
            !Comparing.equal(namesPropName, suppressUntilConfigStorage.getAnnotationNamesPropName());
    }
}
