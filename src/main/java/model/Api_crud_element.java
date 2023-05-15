package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Api_crud_element {
    private int userId;
    private int id;
    private String  title;
    private String body;

    public Api_crud_element(Object o) {
        String[] tab = o.toString().split(",");
        String[] tab0 = tab[0].split("=");
        String[] tab1 = tab[1].split("=");
        String[] tab2 = tab[2].split("=");
        String[] tab3 = tab[3].split("=");
        double userIdFromOject = Double.parseDouble(tab0[1]);
        this.userId = (int) userIdFromOject;
        double idFromObject = Double.parseDouble(tab1[1]);
        this.id = (int) idFromObject;
        this.title = tab2[1];
//        this.body = tab3[1];
        this.body = "change";
    }
}
