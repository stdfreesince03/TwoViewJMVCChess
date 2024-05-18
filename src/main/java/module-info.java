module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires jdk.unsupported.desktop;

    opens com.example.Proj to javafx.fxml;

    exports com.example.Proj;
}