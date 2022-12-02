package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppFunctionsTest {
    AppFunctions actions;

    @BeforeEach
    void runBefore() {
        actions = new AppFunctions("./data/data.json");
    }

    @Test
    void testConstructor() {
        assertEquals(new ArrayList<>(), actions.getQuizzes());
    }
}
