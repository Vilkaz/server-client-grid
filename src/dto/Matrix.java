package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Vilius Kukanauskas
 * Package: dto
 * Date: 26.08.2015
 * Time: 2:48 PM
 */
public class Matrix {
    private HashMap<Integer, ArrayList<Integer>> value;



    public HashMap<Integer, ArrayList<Integer>> getValue() {
        return value;
    }

    public void setValue(HashMap<Integer, ArrayList<Integer>> value) {
        this.value = value;
    }
}
