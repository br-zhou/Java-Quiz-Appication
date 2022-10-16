package model;

import model.questions.Question;

public class InputOutputForTests extends InputOutput {
        @Override
        public boolean getPermission() {
            return false;
        }

        @Override
        public String getString() {
            return "test";
        }

        @Override
        public String getNonEmptyString() {
            return "abc";
        }

        @Override
        public int getInt() {
            return 1;
        }

        @Override
        public int getIntWithinRange(int min, int max) {
            return min;
        }

        @Override
        public void displayQuestion(Question question) {
        }
}
