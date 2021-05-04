package com.company.gui;

import com.company.domain.AccountManagement;
import com.company.domain.IAccountManagement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static com.company.common.Tools.getResourceAsImage;


public class GUI extends Application implements CallbackHandler {
    private final IAccountManagement aMgt = new AccountManagement();
    private final HomepageController homepageController;
    private Node currentContent;
    private Node previousContent;

    public GUI() {
        this.homepageController = new HomepageController(this);

        show(Type.RECENTLY_AND_REVIEW); // Load default content

        update();
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Credit Management System");
        primaryStage.getIcons().add(getResourceAsImage("/images/icon.png"));
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(630);
        primaryStage.setScene(new Scene(homepageController));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                quit();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void list(Type type) {
        if (type == Type.ACCOUNT) {
            setContent(new AccountsOverviewController(this));
        } else if (type == Type.CREDIT) {
            setContent(new CreditsOverviewController(this));
        } else if (type == Type.CREDIT_GROUP) {
            // TODO: implants credit groups view
        } else if (type == Type.PRODUCTION) {
            setContent(new ProductionsOverviewController(this));
        } else if (type == Type.PRODUCER) {
            // TODO: implant producers view
        } else if (type == Type.SEARCH) {
            // TODO: implant search view
        }
    }

    @Override
    public void show(Type type) {
        show(type, null);
    }

    @Override
    public void show(Type type, String uuid) {
        if (type == Type.ACCOUNT) {
            // TODO: implants account view
        } else if (type == Type.CREDIT) {
            setContent(new CreditViewController(uuid, this));
        } else if (type == Type.CREDIT_GROUP) {
            // TODO: implants credit group view
        } else if (type == Type.PRODUCTION) {
            setContent(new ProductionViewController(uuid,this));
        } else if (type == Type.LOGIN) {
            setContent(new LoginController(this));
        } else if (type == Type.RECENTLY_AND_REVIEW) {
            setContent(new RecentlyAndReviewController());
        }
    }

    @Override
    public void show(Type type, AlertType alertType, String message) {
        if (type == Type.MESSAGE) {
            new MessageDialog(alertType, message);
        }
    }

    @Override
    public void add(Type type) {
        if (type == Type.ACCOUNT) {
            // TODO: implants add account
        } else if (type == Type.CREDIT) {
            setContent(new CreditCreationController(this));
        } else if (type == Type.CREDIT_GROUP) {
            // TODO: implants add credit group
        } else {
            // TODO: implants add production
        }
    }

    @Override
    public void edit(Type type, String uuid) {
        if (type == Type.ACCOUNT) {
            // TODO: implants edit account
        } else if (type == Type.CREDIT) {
            // TODO: implants edit credit
        } else if (type == Type.CREDIT_GROUP) {
            // TODO: implants edit credit group
        } else {
            // TODO: implants edit production
        }
    }


    @Override
    public void logout() {
        new AccountManagement().logout();
        update();
    }

    @Override
    public void loginSuccess() {
        if (!(previousContent instanceof LoginController)) {
            setContent(previousContent);
        }
        update();
    }

    @Override
    public void loginFailed(AlertType alertType, String message) {
        show(Type.MESSAGE, alertType, message);
    }


    private void update() {
        UpdateHandler updateInterface;

        // Update container
        updateInterface = homepageController;
        // skip call.hasAccess(). We do not need to check if there is access to the container
        updateInterface.update();

        // Update container content
        updateInterface = (UpdateHandler) currentContent;
        if (!updateInterface.hasAccess(aMgt.getCurrentUser().getAccessLevel())) {
            show(Type.RECENTLY_AND_REVIEW);
        }
        updateInterface.update();
    }

    public void setContent(Node node) {
        previousContent = currentContent;
        currentContent = node;
        homepageController.setContent(node);
    }

    public void quit() {
        Platform.exit();
    }
}
