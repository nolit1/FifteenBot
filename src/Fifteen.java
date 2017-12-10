import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.*;

/**
 * Метод bot() активирует бота
 */


class Fifteen {
    private static int mMax;
    private static HashMap<Integer, Button> buttons = new HashMap<>();
    private static Stage primaryStage = new Stage();

    /**
     * ROOTOFBOARDSIZE - ширина и высота поля. Можно менять, всё будет работать
    */
    private static final int ROOTOFBOARDSIZE = 5;
    private static final int BOARDSIZE = ROOTOFBOARDSIZE*ROOTOFBOARDSIZE;
    private static final int numForShuffle = 50;

    public static int getROOTOFBOARDSIZE() {
        return ROOTOFBOARDSIZE;
    }


//    private Fifteen() {
//        int counter = BOARDSIZE;
//        GridPane root = getGridPane();
//
//        //создаются кнопки
//        for (int i = 1; i < BOARDSIZE + 1; i++) {
//            createButton(i);
//        }
//
//        //заполнение поля кнопками
//        for (int i = mMax - 1; i >= 0; i--) {
//            for (int j = mMax - 1; j >= 0; j--) {
//                root.add(buttons.get(counter--), j, i);
//            }
//        }
//    }

    //перемешка
    private static void start() throws Exception {
        int counter = BOARDSIZE;
        GridPane root = getGridPane();

        primaryStage.setTitle("Пятнашки с ботом");
        primaryStage.setScene(new Scene(root));

        //создаются кнопки
        for (int i = 1; i < BOARDSIZE + 1; i++) {
            createButton(i);
        }

        //заполнение поля кнопками
        for (int i = mMax - 1; i >= 0; i--) {
            for (int j = mMax - 1; j >= 0; j--) {
                root.add(buttons.get(counter--), j, i);

            }
        }

        primaryStage.show();
        shuffle(numForShuffle);

        primaryStage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.R) {
                bot();
            }
        });
    }

    private static void bot() {
        List<List<Integer>> a = HelpKt.aStar(actualBoard());
        List<Integer> n = HelpKt.roadToFinal(a);
        System.out.println(n);
        primaryStage.getScene().setOnKeyPressed(e -> {
            if (n.size() != 0 && e.getCode() == KeyCode.ENTER) {
                toMakeMove(n.get(0));
                n.remove(0);
            }
        });

    }

    private static GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;

    }

    private static void createButton(int i) {
        buttons.put(i, new Button());
        buttons.get(i).setText("" + i);
        buttons.get(i).setId("" + i);
        buttons.get(i).setPrefSize(40, 40);
        if (i == BOARDSIZE) {
            buttons.get(BOARDSIZE).setVisible(false);
        }
        String mButtonId = buttons.get(i).getId();
        buttons.get(i).setOnAction(event -> {
            makeMove(mButtonId);
//            System.out.println(actualBoard());
//            System.out.println("----------------");
//            System.out.println(HelpKt.allMoves(actualBoard()));
//            System.out.println("----------------");
//            //System.out.println(HelpKt.aStar(actualBoard()));
//            System.out.println(aStarSolution(actualBoard()));
//            List<List<Integer>> a = HelpKt.aStar(actualBoard());
//            System.out.println(a);
//            System.out.println(HelpKt.roadToFinal(a));
        });
        //   buttons.get(i).setOnAction(event -> makeMove(mButtonId));
        //buttons.get(i).setOnAction(event -> System.out.println(aStarSolution()));//solutionToScreen(getWhat()));
    }

    //получить индекс кнопки по её координатам
    private static int getButtonIndexByXY(int row, int column) {
        int result = 0;
        for (int i = 1; i < BOARDSIZE + 1; i++) {
            if (GridPane.getRowIndex(buttons.get(i)) == row && GridPane.getColumnIndex(buttons.get(i)) == column) {
                result = i;
                break;
            }
        }
        return result;
    }

    //акутальное состояние доски, получаем с "экрана" для передачи в алгоритм
    private static List<Integer> actualBoard() {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < mMax; i++) {
            for (int j = 0; j < mMax; j++) {
                out.add(getButtonIndexByXY(i, j));
            }
        }
        return out;
    }

    //движение при нажатии мышкой
    private static void makeMove(String mButtonId) {
        int mRow0 = GridPane.getRowIndex(buttons.get(BOARDSIZE));
        int mColumn0 = GridPane.getColumnIndex(buttons.get(BOARDSIZE));
        int mRow = GridPane.getRowIndex(buttons.get(Integer.parseInt(mButtonId)));
        int mColumn = GridPane.getColumnIndex(buttons.get(Integer.parseInt(mButtonId)));
        if (mRow == mRow0 && (mColumn == mColumn0 + 1 || mColumn == mColumn0 - 1) || mColumn == mColumn0 && (mRow == mRow0 + 1 || mRow == mRow0 - 1)) {
            GridPane.setConstraints(buttons.get(BOARDSIZE), mColumn, mRow);
            GridPane.setConstraints(buttons.get(Integer.parseInt(mButtonId)), mColumn0, mRow0);
        }
    }

    //добавленный метод, движение без взаимодействия с мышкой
    private static void toMakeMove(int num) {
        int mRow0 = GridPane.getRowIndex(buttons.get(BOARDSIZE));
        int mColumn0 = GridPane.getColumnIndex(buttons.get(BOARDSIZE));
        int mRow = GridPane.getRowIndex(buttons.get((num)));
        int mColumn = GridPane.getColumnIndex(buttons.get((num)));
        if (mRow == mRow0 && (mColumn == mColumn0 + 1 || mColumn == mColumn0 - 1) || mColumn == mColumn0 && (mRow == mRow0 + 1 || mRow == mRow0 - 1)) {
            GridPane.setConstraints(buttons.get(BOARDSIZE), mColumn, mRow);
            GridPane.setConstraints(buttons.get((num)), mColumn0, mRow0);
        }
    }

    //Список чисел, От двух до четырёх возможных ходов
    private static List<Integer> allMoves() { //2-4
        ArrayList<Integer> movesList = new ArrayList<>();
        //находим пустую клетку
        //находим соседние индексы
        //проверяем клетки по индексам
        int mRow0 = GridPane.getRowIndex(buttons.get(BOARDSIZE));
        int mColumn0 = GridPane.getColumnIndex(buttons.get(BOARDSIZE));

        if (mColumn0 - 1 >= 0) {
            int num = getButtonIndexByXY(mRow0, mColumn0 - 1);
            if (isTrueMove(num)) movesList.add(num);
        }
        if (mColumn0 + 1 <= mMax - 1) {
            int num = getButtonIndexByXY(mRow0, mColumn0 + 1);
            if (isTrueMove(num)) movesList.add(num);
        }
        if (mRow0 - 1 >= 0) {
            int num = getButtonIndexByXY(mRow0 - 1, mColumn0);
            if (isTrueMove(num)) movesList.add(num);
        }
        if (mRow0 + 1 <= mMax - 1) {
            int num = getButtonIndexByXY(mRow0 + 1, mColumn0);
            if (isTrueMove(num)) movesList.add(num);
        }
        return movesList;
    }

    //проверка возможных ходов
    private static boolean isTrueMove(int indexButton) {
        if ((GridPane.getRowIndex(buttons.get(indexButton)) < 0)
                || (GridPane.getRowIndex(buttons.get(indexButton)) >= mMax)) {
            return false;
        }
        if ((GridPane.getColumnIndex(buttons.get(indexButton)) < 0)
                || (GridPane.getColumnIndex(buttons.get(indexButton)) >= mMax)) {
            return false;
        }
        int dx = GridPane.getRowIndex(buttons.get(BOARDSIZE)) - GridPane.getRowIndex(buttons.get(indexButton));
        int dy = GridPane.getColumnIndex(buttons.get(BOARDSIZE)) - GridPane.getColumnIndex(buttons.get(indexButton));

        /*Сумма по модулю = 1, т.к. у соседних
        *либо X либо Y отличается на 1 и
        *либо Y либо X отличается на 0
        */
        return (Math.abs(dx) + Math.abs(dy) == 1) && (dx * dy == 0);
    }

    //перемешка
    private static void shuffle(int quantity) {
        for (int i = 0; i < quantity; i++) {
            List<Integer> possible = allMoves();
            int which = possible.get((int) (Math.random() * possible.size()));
            toMakeMove(which);
        }
    }


    //кодичество клеток не на своём месте, не используется (не относится к боту)
//    private static int quantityNotTrueTiles() {
//        int counter = 0;
//        int res = 0;
//        for (int i = 0; i < mMax; i++) {
//            for (int j = 0; j < mMax; j++) {
//                counter++;
//                if (!(GridPane.getRowIndex(buttons.get(counter)) == i
//                        && GridPane.getColumnIndex(buttons.get(counter)) == j)) res++;
//            }
//        }
//        return res;
//    }
    static void Launch() {
        mMax = BOARDSIZE / getROOTOFBOARDSIZE();
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
