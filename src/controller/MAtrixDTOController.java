package controller;

import dto.MatrixDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkaz on 01.09.2015.
 */
public class MatrixDTOController {
    public static MatrixDTO getMatrixByList(ArrayList<List<Integer>> list) {
        MatrixDTO result = new MatrixDTO();
        setMatrixValueByList(result, list);
        return result;
    }

    private static void setMatrixValueByList(MatrixDTO matrix, ArrayList<List<Integer>> bigList) {
        for (List<Integer> list : bigList) {
            int counter=0;
            for (Integer field:list){
                matrix.setTrue(counter, field);
            }
            counter++;
        }
    }
}
