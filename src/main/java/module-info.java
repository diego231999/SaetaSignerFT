module com.saeta.saetasigner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.naming;
    requires kernel;
    requires sign;
    requires org.bouncycastle.provider;
    requires io;
    requires org.bouncycastle.pkix;
    requires forms;
    requires spring.web;
    requires com.google.gson;
    requires org.apache.commons.io;
    requires layout;
    requires java.logging;
    requires org.slf4j;


    exports com.saeta.saetasigner;
    exports com.saeta.saetasigner.controller;

    opens com.saeta.saetasigner.controller to javafx.fxml;
    opens com.saeta.saetasigner.model to com.google.gson;
}