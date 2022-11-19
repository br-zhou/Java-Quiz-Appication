package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GuiAppFunctions;

import static org.junit.jupiter.api.Assertions.*;

public class AppLogicTest {
    GuiAppFunctions invalidAppActions;
    GuiAppFunctions corruptAppActions;
    GuiAppFunctions defaultAppActions;

    @BeforeEach
    void runBefore() {
        invalidAppActions = new GuiAppFunctions("/c/tmp/home");
        corruptAppActions = new GuiAppFunctions("./data/testCorruptedFile.json");
        defaultAppActions = new GuiAppFunctions("./data/testFile.json");
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
