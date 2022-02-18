package j2hw5;

public class Main {
    static final int SIZE = 10_000_00;
    static final int HALF = SIZE / 2;


    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        firstMethod();
        secondMethod();

    }

    public static void firstMethod() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;

        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long time1 = System.currentTimeMillis();
        System.currentTimeMillis();
        System.out.println("Время выполнения первого потока: " + (time1 - time) + " ms.");

    }

    public static void secondMethod() {


        long time = System.currentTimeMillis();
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        int[] leftHalf = new int[HALF];
        int[] rightHalf = new int[HALF];
        System.arraycopy(arr, 0, leftHalf, 0, HALF);
        System.arraycopy(arr, HALF, rightHalf, 0, HALF);
        Thread thread = new Thread(() -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i/ 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + (i-SIZE)/ 5) * Math.cos(0.2f + (i-SIZE) / 5) * Math.cos(0.4f + (i-SIZE) / 2));
            }
        });
        thread.start();
        thread2.start();
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.arraycopy(arr, 0, arr, 0, HALF);
        System.arraycopy(arr, 0, arr, HALF, HALF);


        long time1 = System.currentTimeMillis();
        System.currentTimeMillis();
        System.out.println("Время выполнения второшго потока: " + (time - time1) + " ms.");


    }
}
