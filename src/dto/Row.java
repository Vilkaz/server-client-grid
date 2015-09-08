package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vilius Kukanauskas
 * Package: dto
 * Date: 02.09.2015
 * Time: 3:12 PM
 */
public class Row extends ArrayList {
    public Boolean[] value = new Boolean[5];

    public Row(List<Integer> integers) {
        for (int index : integers) {
            value[index] = true;
        }
    }

}
