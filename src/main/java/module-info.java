module school.coda.joshua_hugo.bataillejavale {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens school.coda.joshua_hugo.bataillejavale to javafx.fxml;
    exports school.coda.joshua_hugo.bataillejavale;
}