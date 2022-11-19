package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppLogicTest {
    AppFunctions invalidAppActions;
    AppFunctions corruptAppActions;
    AppFunctions defaultAppActions;

    @BeforeEach
    void runBefore() {
        invalidAppActions = new AppFunctions("/c/tmp/home");
        corruptAppActions = new AppFunctions("./data/testCorruptedFile.json");
        defaultAppActions = new AppFunctions("./data/testFile.json");
    }

    @Test
    void testConstructor() {
        assertEquals(0, invalidAppActions.getQuizzes().size());
        assertEquals(0, defaultAppActions.getQuizzes().size());
    }

    @Test
    void testAddQuiz() {

    }
}
