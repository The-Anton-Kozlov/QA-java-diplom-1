package praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    public Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient0;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    @Before
    public void makeBurger() {
        burger = new Burger();
    }

    @Test
    public void setBunTest() {
        burger.setBuns(bun);

        when(bun.getName()).thenReturn("Название булочки");
        assertEquals("Взяли не ту булочку", "Название булочки", burger.bun.getName());
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(ingredient0);

        assertEquals("В бургер добалили 1 ингредиент", 1, burger.ingredients.size());
        assertTrue("Ингредиент не добавлен", burger.ingredients.contains(ingredient0));
    }

    @Test
    public void removeIngredientTest() {
        burger.ingredients.add(ingredient0);
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);

        burger.removeIngredient(1);

        assertEquals("Количество ингредиентов должно быть равно 2", 2, burger.ingredients.size());
        assertTrue("Первый ингредиент отсутствует", burger.ingredients.contains(ingredient0));
        assertTrue("Удалённый ингредиент всё ещё присутствует", !burger.ingredients.contains(ingredient1));
        assertTrue("Третий ингредиент отсутствует", burger.ingredients.contains(ingredient2));
    }


    @Test
    public void moveIngredientTest() {
        burger.ingredients.add(ingredient0);
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);
        burger.moveIngredient(1, 0);

        assertEquals("Количество ингредиентов изменилось", 3, burger.ingredients.size());
        assertTrue("Один или несколько ингредиентов отсутствуют",
                burger.ingredients.contains(ingredient0) &&
                        burger.ingredients.contains(ingredient1) &&
                        burger.ingredients.contains(ingredient2));
        assertEquals("Ингредиент перемещен не правильно", burger.ingredients.get(0), ingredient1);
        assertEquals("Ингредиент перемещен не правильно", burger.ingredients.get(1), ingredient0);
    }

    @Test
    public void priceWithIngredientsTest() {
        burger.bun = bun;
        burger.ingredients.add(ingredient0);
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);

        when(bun.getPrice()).thenReturn(200.0F);
        when(ingredient0.getPrice()).thenReturn(80.0F);
        when(ingredient1.getPrice()).thenReturn(45.0F);
        when(ingredient2.getPrice()).thenReturn(45.0F);


        float expectedPrice = 200.0f * 2 + 80.0f + 45.0f + 45.0f;

        assertEquals("Неправильная общая стоимость бургера",
                expectedPrice, burger.getPrice(), 0.0F);

    }

    @Test
    public void getReceiptTest() {
        burger.bun = bun;
        burger.ingredients.add(ingredient0);
        burger.ingredients.add(ingredient1);
        burger.ingredients.add(ingredient2);

        when(bun.getName()).thenReturn("bun");
        when(bun.getPrice()).thenReturn(200.0F);

        when(ingredient0.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient0.getName()).thenReturn("ingredient0");
        when(ingredient0.getPrice()).thenReturn(80.0F);

        when(ingredient1.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient1.getName()).thenReturn("ingredient1");
        when(ingredient1.getPrice()).thenReturn(45.0F);

        when(ingredient2.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient2.getName()).thenReturn("ingredient2");
        when(ingredient2.getPrice()).thenReturn(45.0F);


        String expectedReceipt = "(==== bun ====)\r\n" +
                "= filling ingredient0 =\r\n" +
                "= filling ingredient1 =\r\n"    +
                "= sauce ingredient2 =\r\n" +
                "(==== bun ====)\r\n" +
                "\r\n" +
                "Price: 570,000000\r\n";

        String actualReceipt = burger.getReceipt();

        assertEquals(
                "Чеки не совпадают!",
                expectedReceipt.trim(),
                actualReceipt.trim()
        );
    }
}
