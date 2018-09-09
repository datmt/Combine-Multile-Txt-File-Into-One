package core;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Controller {
    @FXML
    BorderPane root;

    @FXML
    ImageView imageView;

    @FXML
    TextField destinationTF;

    List<File> allTxtFiles;
    File destinationFile;

    @FXML
    public void initialize()
    {
        imageView.setImage(new Image("https://binarycarpenter.com/wp-content/uploads/2018/09/bc-logo.png"));
    }



    public void selectFilesToCombine()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select .txt files to combine");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT File", "*.txt"));
        allTxtFiles = chooser.showOpenMultipleDialog(root.getScene().getWindow());
    }

    private String readFileToString(File file) throws IOException
    {
        StringBuilder content = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "UTF8"));

        String line;
        while ((line = in.readLine()) != null)
        {
            content.append(line + "\n");
        }

        System.out.println("content is: " + content.toString());
        return content.toString();
    }

    public void selectDestinationFile()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Set destination file");
        destinationFile = chooser.showOpenDialog(root.getScene().getWindow());
        destinationTF.setText(destinationFile.getAbsolutePath());
    }

    public void combineNow()
    {
        if (destinationFile == null || allTxtFiles == null || allTxtFiles.size() == 0)
            return;

        try
        {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(destinationFile.getAbsolutePath(), true), // true to append
                            StandardCharsets.UTF_8                  // Set encoding
                    )
            );

            for (File f : allTxtFiles)
            {
                out.write(readFileToString(f));
                System.out.println("write one file");
            }

            out.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void openBinaryCarpenter()
    {
        try {
            Desktop.getDesktop().browse(new URI("https://binarycarpenter.com/"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
