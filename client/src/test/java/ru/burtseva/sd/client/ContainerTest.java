package ru.burtseva.sd.client;

import ru.burtseva.sd.client.command.stock.AddStockCommand;
import ru.burtseva.sd.client.command.stock.AllStocksCommand;
import ru.burtseva.sd.client.command.stock.UpdatePriceCommand;
import ru.burtseva.sd.client.command.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"deprecation", "unused"})
@Testcontainers
public class ContainerTest {
    private static final String LOGIN = "test";
    private static final String COMPANY = "test";

    @Container
    private final GenericContainer<?> __ = new FixedHostPortGenericContainer<>("docker.io/library/stock:1.0")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    @Test
    public void registerNewUser() {
        var user = new RegisterUserCommand(LOGIN).execute();
        Assertions.assertEquals(LOGIN, user.getLogin());
    }

    @Test
    public void registerUserTwice() {
        var command = new RegisterUserCommand(LOGIN);
        command.execute();
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void authorizeUser() {
        var user = new RegisterUserCommand(LOGIN).execute();
        var authorizedUser = new AuthorizeCommand(LOGIN).execute();
        Assertions.assertEquals(user.getLogin(), authorizedUser.getLogin());
    }

    @Test
    public void authorizeWithoutRegister() {
        var command = new AuthorizeCommand(LOGIN);
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void addStock() {
        var stock = new AddStockCommand(COMPANY, 1L, 1L).execute();
        Assertions.assertEquals(COMPANY, stock.getCompany());
    }

    @Test
    public void addStockTwice() {
        var command = new AddStockCommand(COMPANY, 1L, 1L);
        command.execute();
        assertThrows(RuntimeException.class, command::execute);
    }

    @Test
    public void allStocks() {
        var stock = new AddStockCommand(COMPANY, 1L, 1L).execute();
        var allStocks = new AllStocksCommand().execute();

        Assertions.assertEquals(1, allStocks.size());
        Assertions.assertEquals(stock, allStocks.get(0));
    }

    @Test
    public void updatePrice() {
        var stock = new AddStockCommand(COMPANY, 1L, 1L).execute();
        var newPrice = 2L;
        var newStock = new UpdatePriceCommand(COMPANY, newPrice).execute();
        Assertions.assertEquals(newPrice, newStock.getPrice());
    }

    @Test
    public void getMoneyAndAddMoney() {
        var user = new RegisterUserCommand(LOGIN).execute();
        Assertions.assertEquals(0L, user.getMoney());
        var addMoneyValue = 1L;
        Assertions.assertTrue(new AddMoneyCommand(LOGIN, addMoneyValue).execute());
        user = new AuthorizeCommand(LOGIN).execute();
        Assertions.assertEquals(addMoneyValue, user.getMoney());
    }

    @Test
    public void addInvalidMoney() {
        var user = new RegisterUserCommand(LOGIN).execute();
        Assertions.assertEquals(0L, user.getMoney());
        var command = new AddMoneyCommand(LOGIN, -1L);
        assertThrows(RuntimeException.class, command::execute);
    }
}
