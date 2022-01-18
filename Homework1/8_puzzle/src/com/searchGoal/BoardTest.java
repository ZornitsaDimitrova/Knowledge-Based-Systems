package com.searchGoal;

import java.util.Arrays;

public class BoardTest {

    public static void printState(State state) {

        if (state.getParent() == null) {
            System.out.println(state.getOperator());
            return;
        }

        System.out.println(state.getOperator());
        printState(state.getParent());
    }

    public static void main(String[] args) {

        Board exampleBoard = new Board();

        System.out.println(Arrays.toString(exampleBoard.getData()));

        System.out.println(exampleBoard);

        Board movedUp = exampleBoard.moveUp();
        if (movedUp != null) {
            System.out.println(movedUp);
        }
        System.out.println(exampleBoard.moveDown());
        System.out.println(exampleBoard.moveRight());
        System.out.println(exampleBoard.moveLeft());


        State exampleState = new State(exampleBoard, null, 0, 0, "start");

        int md,noub;

        md= Heuristics.manhattanDistance(exampleState.getBoard());
        System.out.printf("Manhattan Distance for example board: %d\n", md);

        noub = Heuristics.numberOfUnorderedBlocks(exampleState.getBoard());
        System.out.printf("Number of unordered blocks for example board: %d\n", noub);

        System.out.println("\n\n--------------------Testing A* search------------------------\n\n");

        //--------------------Manhattan Distance-------------------------

        long startTime = System.currentTimeMillis();
        State result = SearchStrategies.aStar(exampleState, Heuristics::manhattanDistance);
        long stopTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        //колко време отнема и колко памет използва
        System.out.println("Time taken: " + (stopTime - startTime));
        System.out.println("Used memory in bytes: " + (runtime.totalMemory() - runtime.freeMemory()));

        if (result == null) {
            System.exit(0);
        }

        System.out.println(result);
        System.out.println("\n\nThe path from h(n) = Manhattan Distance:\n\n");
        printState(result);


        //--------------------Number of unordered blocks-------------------------
        startTime = System.currentTimeMillis();
        result = SearchStrategies.aStar(exampleState, Heuristics::numberOfUnorderedBlocks);
        stopTime = System.currentTimeMillis();
        runtime = Runtime.getRuntime();
        runtime.gc();


        if (result == null) {
            System.exit(0);
        }

        //колко време отнема и колко памет използва
        System.out.println("Time taken: " + (stopTime - startTime));
        System.out.println("Used memory in bytes: " + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println(result);
        System.out.println("\n\nThe path from h(n) = number of unordered blocks:\n\n");
        printState(result);

        System.out.println("\n\n--------------------Testing best-first search------------------------\n\n");

        //--------------------Manhattan Distance-------------------------

        result = SearchStrategies.bestFirstSearch(exampleState, Heuristics::manhattanDistance);
        if (result == null) {
            System.exit(0);
        }
        System.out.println("\n\nThe path from h(n) = Manhattan Distance:\n\n");
        printState(result);


        //--------------------Number of unordered blocks-------------------------

        result = SearchStrategies.bestFirstSearch(exampleState, Heuristics::numberOfUnorderedBlocks);
        if (result == null) {
            System.exit(0);
        }
        System.out.println("\n\nThe path from h(n) = number of unordered blocks:\n\n");
        printState(result);

    }

}
