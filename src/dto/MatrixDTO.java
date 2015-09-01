package dto;

import java.util.ArrayList;

/**
 * User: Vilius Kukanauskas
 * Package: dto
 * Date: 26.08.2015
 * Time: 2:48 PM
 */
public class MatrixDTO {
    private Boolean[][] value = new Boolean[5][5];

    public void setTrueByList(ArrayList<ArrayList<Integer>> list){
        for (ArrayList<Integer> miniList : list){
            setTrue(miniList.get(0), miniList.get(1));
        }
    }

     public void setTrue(int operator1, int operator2) {
        value[operator1][operator2] = true;
    }

}
