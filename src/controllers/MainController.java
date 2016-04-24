package controllers;

import encript.VigenereEncryptor;
import encript.VigenereEncryptor.Actions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MainController implements Initializable {

    private static final int MESSAGE_GAP = 15;
    private static final Font MESSAGE_FONG = new Font("Arial", 16);

    @FXML
    private Pane rootPane;
    @FXML
    private TextField offsetField;
    @FXML
    private TextField keyTextField;
    @FXML
    private TextArea sourceTextArea;
    @FXML
    private TextArea resultTextArea;

    private FileChooser fileChooser;
    private VigenereEncryptor encryptor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        ExtensionFilter txtExtensionFilter = new ExtensionFilter("Текстовый файл", "*.txt");
        fileChooser.getExtensionFilters().add(txtExtensionFilter);
        fileChooser.setTitle("Открыть файл");
        encryptor = new VigenereEncryptor();
    }

    @FXML
    private void encryptFile() {
        File file = fileChooser.showOpenDialog(null);
        String source = readFile(file);
        sourceTextArea.setText(source);
        encryptText();
    }

    @FXML
    private void decryptFile() {
        File file = fileChooser.showOpenDialog(null);
        String source = readFile(file);
        resultTextArea.setText(source);
        decryptText();
    }

    @FXML
    private void onEncryptEvent() {
        encryptText();
    }

    @FXML
    private void onDecryptEvent() {
        decryptText();
    }

    @FXML
    private void onClearEvent() {
        sourceTextArea.clear();
        resultTextArea.clear();
    }

    private void encryptText() {
        if (offsetField.getText().isEmpty() || keyTextField.getText().isEmpty()) {
            showMessage("Невозможно зашифровать. Отсутствуют входные параметры!");
            return;
        }
        String source = sourceTextArea.getText();
        initEncryptor();
        String result = encryptor.convertText(source, Actions.ENCRYPT);
        resultTextArea.setText(result);
    }

    private void decryptText() {
        if (offsetField.getText().isEmpty() || keyTextField.getText().isEmpty()) {
            showMessage("Невозможно расшифровать. Отсутствуют входные параметры!");
            return;
        }
        String source = resultTextArea.getText();
        initEncryptor();
        String result = encryptor.convertText(source, Actions.DECRYPT);
        sourceTextArea.setText(result);
    }

    private void initEncryptor() throws NumberFormatException {
        int offset = Integer.parseInt(offsetField.getText());
        String keyWord = keyTextField.getText().toLowerCase();
        if (encryptor.getOffset() != offset || !encryptor.getKeyWord().equals(keyWord)) {
            encryptor.setParameters(keyWord, offset);
        }
    }

    private String readFile(File file) {
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (IOException ex) {
                System.out.println("Не удалось прочитать файл");
            }
        }
        return "";
    }

    private void showMessage(String message) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setFont(MESSAGE_FONG);
        label.setTextFill(Color.RED);
        label.setOnMouseReleased(me -> {
            popup.hide();
        });
        popup.getContent().add(label);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        double x = stage.getX();
        double y = stage.getY();
        double w = stage.getWidth();
        double h = stage.getHeight();
        popup.show(stage);
        popup.setX(x + w - (popup.getWidth() + MESSAGE_GAP));
        popup.setY(y + h - (popup.getHeight() + MESSAGE_GAP));
    }
}
