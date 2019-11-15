import java.util.HashMap;

public class Testing {
    public static void main(String[] args) {
        int[] arr = {2,3,4,1};
        printArr(getElements(arr));



    }

    public static int[] getElements(int[]arr){
        int[] output = new int[arr.length];
        int frontCount = 0;
        int backCount = arr.length - 1;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] % 2 == 0){
                output[frontCount] = arr[i];
                frontCount++;
            }
            else if(arr[i] % 2 == 1){
                output[backCount] = arr[i];
                backCount--;
            }
        }
        return output;
    }

    public static void printArr(int[] arr){
        for(int i = 0;i < arr.length;i++){
            System.out.print(arr[i]);
        }
    }

}
