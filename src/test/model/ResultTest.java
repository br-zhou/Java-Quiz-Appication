package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
    private Result result1;
    private Result result2;
    private Result result3;

    @BeforeEach
    public void runBefore() {
        result1 = new Result(0);
        result2 = new Result(3);
        result3 = new Result(25);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, result1.getScore());
        assertEquals(0, result2.getScore());
        assertEquals(0, result3.getScore());

        assertEquals(0, result1.getMaxScore());
        assertEquals(3, result2.getMaxScore());
        assertEquals(25, result3.getMaxScore());
    }

    @Test
    public void testIncrement() {
        result1.incrementScore();
        assertEquals(1, result1.getScore());

        for (int i = 0; i < 10; i++) {
            result2.incrementScore();
        }
        assertEquals(10, result2.getScore());
    }

    @Test
    public void testToString() {
        assertEquals("0 / 0", result1.toString());
        assertEquals("0 / 25", result3.toString());

        result1.incrementScore();
        assertEquals("1 / 0", result1.toString());

        for (int i = 0; i < 10; i++) {
            result3.incrementScore();
        }
        assertEquals("10 / 25", result3.toString());
    }
}
