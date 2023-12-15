package Controller;

import Dto.OrderDto;
import Dto.Tm.OrderTm;
import Dto.Tm.OrdersFormTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Impl.OrderModelImpl;
import dao.OrderModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrdersFormController {

    public JFXTreeTableView<OrdersFormTm> tblOrders;
    public TreeTableColumn colOrderID;
    public TreeTableColumn colDate;
    public TreeTableColumn colCustomerID;



    OrderModel orderModel=new OrderModelImpl();
    public AnchorPane ordersPane;


    public void initialize(){
        colOrderID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colCustomerID.setCellValueFactory(new TreeItemPropertyValueFactory<>("custId"));
        loadOrdersTable();
    }

    private void loadOrdersTable() {
        ObservableList<OrdersFormTm> orderTms = FXCollections.observableArrayList();
        try {
            List<OrderDto> dtoList = orderModel.allOrders();

            for (OrderDto dto:dtoList) {
                orderTms.add(
                    new OrdersFormTm(
                        dto.getOrderId(),
                        dto.getDate(),
                        dto.getCustId()
                    )
                );
            }

            //RecursiveTreeItem treeItem = new RecursiveTreeItem<>(orderTms, RecursiveTreeObject::getChildren);
            RecursiveTreeItem treeItem = new RecursiveTreeItem<>(orderTms, RecursiveTreeObject::getChildren);
            tblOrders.setRoot(treeItem);
            tblOrders.setShowRoot(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ordersPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"))));
        stage.show();
    }
}
