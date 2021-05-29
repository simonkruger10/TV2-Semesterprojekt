package com.company.presentation;

import com.company.common.*;
import com.company.domain.*;
import com.company.presentation.layout.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import static com.company.common.Tools.getResourceAsImage;


public class GUI extends Application implements CallbackHandler {
    private final IAccountManagement accountMgt = new AccountManagement();
    private final CreditManagement creditMgt = new CreditManagement();
    private final CreditGroupManagement creditGroupMgt = new CreditGroupManagement();
    private final ProductionManagement productionMgt = new ProductionManagement();
    private final ProducerManagement producerMgt = new ProducerManagement();
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
        primaryStage.setOnCloseRequest(windowEvent -> quit());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void list(Type type) {
        list(type, 1);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void list(Type type, Integer page) {
        OverviewController overview = new OverviewController(this);

        int offset = 10 * (page - 1);

        //noinspection rawtypes
        IDTO[] dtos = null;
        Integer maxPages = 1;
        if (type == Type.ACCOUNT) {
            dtos = convertToIDTO(accountMgt.list(offset));
            maxPages = calMaxPages(accountMgt.count(), 10);
        } else if (type == Type.CREDIT) {
            dtos = convertToIDTO(creditMgt.list(offset));
            maxPages = calMaxPages(creditMgt.count(), 10);
        } else if (type == Type.CREDIT_GROUP) {
            dtos = convertToIDTO(creditGroupMgt.list(offset));
            maxPages = calMaxPages(creditGroupMgt.count(), 10);
        } else if (type == Type.PRODUCTION) {
            dtos = convertToIDTO(productionMgt.list(offset));
            maxPages = calMaxPages(productionMgt.count(), 10);
        } else if (type == Type.PRODUCER) {
            dtos = convertToIDTO(producerMgt.list(offset));
            maxPages = calMaxPages(producerMgt.count(), 10);
        } else if (type == Type.SEARCH) {
            // TODO: implant search view
        }

        if (dtos != null) {
            overview.showList(type, dtos);
            overview.showNav(page, maxPages);
        }
        setContent(overview);
    }

    @Override
    public void show(Type type) {
        if (type == Type.LOGIN) {
            setContent(new LoginController(this));
        } else if (type == Type.RECENTLY_AND_REVIEW) {
            setContent(new RecentlyAndReviewController());
        }
    }

    @Override
    public void show(Type type, @SuppressWarnings("rawtypes") IDTO dto) {
        if (type == Type.ACCOUNT) {
            setContent(new AccountViewController((IAccount) dto.getDTO(),this));
        } else if (type == Type.CREDIT) {
            ICredit credit = (ICredit) dto.getDTO();
            setContent(new CreditViewController(creditMgt.getByID(credit.getID(), credit.getType()), this));
        } else if (type == Type.CREDIT_GROUP) {
            ICreditGroup creditGroup = (ICreditGroup) dto.getDTO();
            setContent(new CreditGroupViewController(creditGroupMgt.getByID(creditGroup.getID()), this));
        } else if (type == Type.PRODUCTION) {
            IProduction production = (IProduction) dto.getDTO();
            setContent(new ProductionViewController(productionMgt.getByID(production.getID()),this));
        } else if (type == Type.PRODUCER) {
            IProducer producer = (IProducer) dto.getDTO();
            setContent(new ProducerViewController(producerMgt.getByID(producer.getID()),this));
        }
    }

    @Override
    public void show(AlertType alertType, String message) {
        new MessageDialog(alertType, message);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void add(Type type) {
        if (type == Type.ACCOUNT) {
            setContent(new EditAccountController(this));
        } else if (type == Type.CREDIT) {
            setContent(new CreditCreationController(this));
        } else if (type == Type.CREDIT_GROUP) {
            // TODO: implants add credit group
        } else if (type == Type.PRODUCER) {
            // TODO: implants add production
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void edit(Type type, @SuppressWarnings("rawtypes") IDTO dto) {
        if (type == Type.ACCOUNT) {
            EditAccountController editAccountController = new EditAccountController(this);
            editAccountController.loadAccount((IAccount) dto.getDTO());
            setContent(editAccountController);
        } else if (type == Type.CREDIT) {
            ICredit credit = (ICredit) dto.getDTO();
            CreditCreationController creditCreationController = new CreditCreationController(this);
            creditCreationController.loadCredit(creditMgt.getByID(credit.getID(), credit.getType()));
            setContent(creditCreationController);
        } else if (type == Type.CREDIT_GROUP) {
            // TODO: implants edit credit group
        } else if (type == Type.PRODUCER) {
            // TODO: implants edit production
        }
    }


    @Override
    public void logout() {
        accountMgt.logout();
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
        show(alertType, message);
    }

    private Integer calMaxPages(Integer count, @SuppressWarnings("SameParameterValue") Integer countEachPage) {
        int maxPages = count / countEachPage;
        if (count%countEachPage > 0) {
            return ++maxPages;
        }
        return maxPages;
    }

    private void update() {
        UpdateHandler updateInterface;

        // Update container
        updateInterface = homepageController;
        // skip call.hasAccess(). We do not need to check if there is access to the container
        updateInterface.update();

        // Update container content
        updateInterface = (UpdateHandler) currentContent;
        if (!updateInterface.hasAccess(accountMgt.getCurrentUser().getAccessLevel())) {
            show(Type.RECENTLY_AND_REVIEW);
        }
        updateInterface.update();
    }

    private void setContent(Node node) {
        previousContent = currentContent;
        currentContent = node;
        homepageController.setContent(node);
    }

    @SuppressWarnings("rawtypes")
    private <T> IDTO[] convertToIDTO(T[] list) {
        IDTO[] dtos = new IDTO[list.length];
        for (int i = 0; i < list.length; i++) {
            int finalI = i;
            dtos[i] = (IDTO<T>) () -> list[finalI];
        }
        return dtos;
    }

    private void quit() {
        Platform.exit();
    }

}
