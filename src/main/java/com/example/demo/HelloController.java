package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class HelloController {
    @FXML
    ImageView image_view;
    @FXML
    ImageView image_view1;
    @FXML
    ImageView image_view11;
    @FXML
    ImageView image_view111;


    @FXML
    private Label label;

    @FXML
    private TextField inputLength;

    @FXML
    private Button generateButton;

    @FXML
    private Button radixSortButton;

    @FXML
    private Button bitonicSortButton;

    @FXML
    private Button randomizedSortButton;

    @FXML
    private void handleRandomizedSortButton(ActionEvent event) {
        randomizedSort(array);
    }
    @FXML
    private Image image1;
    @FXML
    private TextArea resultText;

    @FXML
    private Button radixAllert;
    @FXML
    private Button bitonicAllert;
    @FXML
    private Button randomAllert;


    private int[] array;

    private void generateArray(int length) {
        array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * 100);
        }
    }
    private void radixSort() {
        int max = Arrays.stream(array).max().getAsInt();
        StringBuilder sb = new StringBuilder();
        for (int exp = 1; max / exp > 0; exp *= 10) {
            int[] output = new int[array.length];
            int[] count = new int[10];
            for (int i = 0; i < array.length; i++) {
                count[(array[i] / exp) % 10]++;
            }
            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }
            for (int i = array.length - 1; i >= 0; i--) {
                output[count[(array[i] / exp) % 10] - 1] = array[i];
                count[(array[i] / exp) % 10]--;
            }
            array = output;
            sb.append(Arrays.toString(array)).append("\n");
        }
        resultText.setText(sb.toString());
    }

    private void bitonicSort(int low, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            bitonicSort(low, k, true);
            bitonicSort(low + k, k, false);
            bitonicMerge(low, cnt, dir);
        }
    }

    private void bitonicMerge(int low, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = greatestPowerOfTwoLessThan(cnt);
            for (int i = low; i < low + cnt - k; i++) {
                compareAndSwap(i, i + k, dir);
            }
            bitonicMerge(low, k, dir);
            bitonicMerge(low + k, cnt - k, dir);
        }
    }

    private void compareAndSwap(int i, int j, boolean dir) {
        if ((array[i] > array[j] && dir) || (array[i] < array[j] && !dir)) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private int greatestPowerOfTwoLessThan(int n) {
        int k = 1;
        while (k < n) {
            k = k << 1;
        }
        return k >> 1;
    }

    // Метод для сортировки массива при помощи алгоритма сортировки Randomized Sort
    public static void randomizedSort(int[] arr) {
        // Проверка на пустой массив и массив с одним элементом
        if (arr == null || arr.length <= 1) {
            return;
        }

        // Вызываем метод для сортировки массива
        randomizedSortHelper(arr, 0, arr.length - 1);
    }

    // Метод для сортировки массива при помощи алгоритма сортировки Randomized Sort
    private static void randomizedSortHelper(int[] arr, int start, int end) {
        // Если начальный индекс >= конечного индекса, то массив отсортирован
        if (start >= end) {
            return;
        }

        // Генерируем случайный индекс элемента в массиве
        Random rand = new Random();
        int pivotIndex = rand.nextInt(end - start + 1) + start;

        // Меняем местами опорный элемент и последний элемент массива
        int temp = arr[pivotIndex];
        arr[pivotIndex] = arr[end];
        arr[end] = temp;

        // Разбиваем массив на две части вокруг опорного элемента
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (arr[j] < arr[end]) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        i++;
        temp = arr[i];
        arr[i] = arr[end];
        arr[end] = temp;

        // Рекурсивно вызываем randomizedSortHelper для левой и правой части массива
        randomizedSortHelper(arr, start, i - 1);
        randomizedSortHelper(arr, i + 1, end);
    }
    public void initialize() {
        Image myImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("2020-01-16-867654001.jpg")));
        image_view.setImage(myImage);
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("hrUDdYC7OH-countingsort.gif")));
        image_view1.setImage(image1);
        Image image11 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("bitonic_sort.gif")));
        image_view11.setImage(image11);
        Image image111 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Quicksort-example.gif")));
        image_view111.setImage(image111);
        inputLength.setText("10");
        generateArray(10);
//        long startTime = System.nanoTime();
//        Arrays.sort(array);
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);

        generateButton.setOnAction(event -> {
            int length = Integer.parseInt(inputLength.getText());
            generateArray(length);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (i % 40 == 0 && i != 0) {
                    sb.append("\n");
                }
                sb.append(array[i]).append(" ");
            }
            long startTime = System.currentTimeMillis();
            Arrays.sort(array);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            long startTime1 = System.nanoTime();
            Arrays.sort(array);
            long endTime1 = System.nanoTime();
            long duration1 = (endTime1 - startTime1);
//            resultText.setText(sb.toString()+" Время выполнения: "+ timeSpent + " миллисекунд" );
            resultText.setText(sb.toString());
            resultText.setText(sb+" \nВремя выполнения: "+ duration + "," + duration1 + " мс");
        });
        radixSortButton.setOnAction(event -> {
            radixSort();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (i % 40 == 0 && i != 0) {
                    sb.append("\n");
                }
                sb.append(array[i]).append(" ");
            }
            long startTime = System.currentTimeMillis();
            Arrays.sort(array);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);

            long startTime1 = System.nanoTime();
            Arrays.sort(array);
            long endTime1 = System.nanoTime();
            long duration1 = (endTime1 - startTime1);
            resultText.setText(sb.toString());
            resultText.setText(sb+" \nВремя выполнения: "+ duration + "," + duration1 + " мс");

        });
        bitonicSortButton.setOnAction(event -> {
            bitonicSort(0, array.length, true);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (i % 40 == 0 && i != 0) {
                    sb.append("\n");
                }
                sb.append(array[i]).append(" ");
            }
            long startTime = System.currentTimeMillis();
            Arrays.sort(array);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            long startTime1 = System.nanoTime();
            Arrays.sort(array);
            long endTime1 = System.nanoTime();
            long duration1 = (endTime1 - startTime1);
//            resultText.setText(sb.toString()+" Время выполнения: "+ timeSpent + " миллисекунд" );
            resultText.setText(sb.toString());
            resultText.setText(sb+" \nВремя выполнения: "+ duration + "," + duration1 + " мс");

        });
        randomizedSortButton.setOnAction(event -> {
            randomizedSort(array);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (i % 40 == 0 && i != 0) {
                    sb.append("\n");
                }
                sb.append(array[i]).append(" ");
            }
            long startTime = System.currentTimeMillis();
            Arrays.sort(array);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);

            long startTime1 = System.nanoTime();
            Arrays.sort(array);
            long endTime1 = System.nanoTime();
            long duration1 = (endTime1 - startTime1);
            resultText.setText(sb.toString());

            resultText.setText(sb+" \nВремя выполнения: "+ duration + "," + duration1 + " мс");

//            resultText.setText(sb+" \nВремя выполнения: "+ duration1 + " НС" );

        });
        radixAllert.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Справка.");
            alert.setHeaderText(null);
            alert.setContentText("""
                    Радиксная сортировка (Radix sort) - это алгоритм сортировки, который использует позиционное представление чисел для упорядочивания элементов в массиве. В отличие от большинства других алгоритмов сортировки, которые сравнивают элементы между собой, радиксная сортировка основывается на их цифровом представлении.
                                        
                    Алгоритм работает путем разбиения списка элементов на корзины (buckets) в зависимости от значения определенного разряда. Затем каждая корзина сортируется отдельно, используя сортировку по подсчету (Counting sort) или другой алгоритм сортировки. Когда все корзины отсортированы, элементы объединяются в исходном порядке.
                                        
                    Радиксная сортировка может использоваться для сортировки элементов любого типа, которые могут быть представлены в виде чисел, включая целые числа, числа с плавающей точкой, даты, строки и т. д. Однако она не подходит для сортировки элементов, которые невозможно преобразовать в числа или для элементов, которые имеют разное количество разрядов.
                                        
                    Радиксная сортировка имеет линейную временную сложность O(nk), где n - количество элементов в массиве, а k - количество разрядов в элементах. Однако она может потребовать больше памяти, чем другие алгоритмы сортировки, так как требует создания временных массивов и корзин для каждого разряда.
                    """);
            alert.showAndWait();
        });
        bitonicAllert.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Справка.");
            alert.setHeaderText(null);
            alert.setContentText("""
                    Сортировка слиянием с использованием Битовых масок (Bitonic merge sort) - это вариант алгоритма сортировки слиянием, который использует битовые маски для упорядочивания элементов в массиве. Он был разработан Джоном Кэнэном (John Kenneth) в 1962 году.
                                        
                    Алгоритм начинается с деления массива на несколько подмассивов, каждый из которых сортируется отдельно. Затем эти подмассивы объединяются в битонические последовательности (bitonic sequences) - последовательности, которые сначала монотонно возрастают, а затем монотонно убывают (или остаются неубывающими).
                                        
                    Далее происходит слияние битонических последовательностей попарно, причем каждая пара сливается в соответствии с битовой маской. Битовая маска указывает, следует ли выполнять обмен элементами в паре при слиянии. Если бит маски равен 1, то элементы должны поменяться местами, а если 0 - то остаться на своих местах.
                                        
                    Процесс слияния битонических последовательностей продолжается до тех пор, пока не останется единственная битоническая последовательность - это и есть отсортированный массив.
                                        
                    Сортировка слиянием с использованием Битовых масок имеет время выполнения O(n log^2 n) и является эффективным алгоритмом сортировки для параллельных вычислений на многопроцессорных системах. Однако он требует дополнительной памяти для хранения битовых масок и может быть менее эффективным, чем обычная сортировка слиянием на однопроцессорных системах.
                                       
                    """);
            alert.showAndWait();
        });
        randomAllert.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Справка.");
            alert.setHeaderText(null);
            alert.setContentText("""
                    Сортировка на основе случайных чисел (Randomized Sort) - это алгоритм сортировки, который использует случайные числа для определения порядка элементов в массиве. Он был разработан в 1980-х годах и является вариантом быстрой сортировки (Quick Sort).
                                        
                    Алгоритм начинается с выбора случайного элемента массива, называемого опорным (pivot). Затем элементы массива сравниваются с опорным элементом и разделяются на две группы: элементы, меньшие опорного, и элементы, большие опорного. Эти две группы сортируются рекурсивно, используя тот же самый алгоритм, пока все элементы не будут отсортированы.
                                        
                    С помощью случайных чисел выбирается опорный элемент, что позволяет избежать худшего случая при сортировке, когда наихудшим выбором опорного элемента может быть первый или последний элемент массива. Более того, случайный выбор опорного элемента обеспечивает более равномерное распределение элементов в разных группах, что ускоряет сортировку в среднем случае.
                                        
                    Временная сложность алгоритма Randomized Sort в среднем составляет O(n log n), как и у быстрой сортировки, но с некоторой вероятностью может быть O(n^2) в худшем случае, хотя это встречается редко при случайном выборе опорного элемента.
                    """);
            alert.showAndWait();
        });
    }

}