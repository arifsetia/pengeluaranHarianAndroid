package id.my.cariberas.pengeluaranharian;

import java.util.ArrayList;
import java.util.List;

public class DataPengeluaran {
    public static List<Grocery> groceryList() {
//        groceryList.add(new Grocery(cursor.getString(0).toString(), cursor.getString(1).toString(), cursor.getString(2).toString(), cursor.getString(3).toString()));

        Grocery telur = new Grocery("telur", "e", "tets", "sjs");
        Grocery sabun = new Grocery("sabun", "d", "test", "sjhs");
        Grocery kopi = new Grocery("kopi", "dd", "sjhs", "jhs");
        Grocery teh = new Grocery("teh", "sss", "hhs", "jss");

        List<Grocery> groceryList = new ArrayList<>();

        groceryList.add(telur);
        groceryList.add(sabun);
        groceryList.add(kopi);
        groceryList.add(teh);

        return groceryList;
    }
}
