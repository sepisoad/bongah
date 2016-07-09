package com.sepisoad.bongah.ui;

import com.sepisoad.bongah.app.App;
import com.sepisoad.bongah.app.Config;
import com.sepisoad.bongah.app.GlobalsKt;
import com.sepisoad.bongah.db.TableLogin;
import com.sepisoad.bongah.ui.view.MainViewController;
import static com.sepisoad.bongah.app.GlobalsKt.*;
import static javafx.application.Platform.exit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jodd.io.FileUtil;
import jodd.io.FileUtilParams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;


public class FxApp extends Application {
  public static Application appObj;
  private Stage primaryStage;
  private MainViewController mainViewCtrl;
  private AnchorPane paneAppUiRoot;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    primaryStage.setTitle(getPROPS().getProperty("appName"));

    String dbPassword = TableLogin.INSTANCE.get();
    if(!dbPassword.matches(GlobalsKt.getEMPTY_PASSWORD())){
      String userPassword = showGetPasswordDialog();
      if(!userPassword.matches(dbPassword)){
        String alertMsg = GlobalsKt.getPROPS().getProperty("alertMsgIncorrectPassword");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GlobalsKt.getPROPS().getProperty("error"));
        alert.setHeaderText(GlobalsKt.getPROPS().getProperty("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
        primaryStage.hide();
        return;
      }
    }

    loadMainView();

    if(Config.INSTANCE.getLayoutDirection().equals("RTL"))
      primaryStage.getScene().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    if(Config.INSTANCE.getLayoutDirection().equals("LTR"))
      primaryStage.getScene().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    primaryStage.setWidth(Config.INSTANCE.getWindowWidth());
    primaryStage.setHeight(Config.INSTANCE.getWindowHeight());

    appObj = this;
  }

  private void loadMainView(){
    ClassLoader cl = FxApp.class.getClassLoader();
    FXMLLoader fxmlLoader = new FXMLLoader();
    try{
      fxmlLoader.setLocation(cl.getResource("res/ui/main.fxml"));
      paneAppUiRoot = fxmlLoader.load();
    }catch (IOException ex){
      ex.printStackTrace();
    }

    primaryStage.setScene(new Scene(paneAppUiRoot));
    primaryStage.show();

    mainViewCtrl = fxmlLoader.getController();
    mainViewCtrl.setAppUi(this);

    primaryStage.getScene().getStylesheets().add(cl.getResource("res/css/base.css").toExternalForm());

    String iconPath = cl.getResource("res/icons/bongah.png").toString();
    primaryStage.getIcons().add(new Image(iconPath));
  }

  private String showGetPasswordDialog() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(GlobalsKt.getPROPS().getProperty("dialogLoginTitle"));
    dialog.setHeaderText(GlobalsKt.getPROPS().getProperty("dialogLoginHeader"));
    dialog.setContentText(GlobalsKt.getPROPS().getProperty("dialogLoginContentText"));

    Optional<String> result = dialog.showAndWait();

    if(result.isPresent())
      return result.get();
    else
      return "";
  }

  public static void bootstrap(String[] args){
    launch (FxApp.class);
  }

  public static void main(String[] args){
    App.INSTANCE.start(args);
    exit();
  }
}
