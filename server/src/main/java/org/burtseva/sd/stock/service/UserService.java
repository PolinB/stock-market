package org.burtseva.sd.stock.service;

import org.burtseva.sd.stock.model.Stock;
import org.burtseva.sd.stock.model.User;
import org.burtseva.sd.stock.repository.StockRepository;
import org.burtseva.sd.stock.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    public UserService(StockService stockService, StockRepository stockRepository, UserRepository userRepository) {
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    public User register(String login) {
        var user = new User();
        user.setLogin(login);
        return userRepository.save(user);
    }

    public void addMoney(String login, Long money) {
        var user = findByLogin(login);
        user.setMoney(user.getMoney() + money);
        userRepository.save(user);
    }

    public List<Stock> getStocks(String login) {
        return findByLogin(login).getStocks();
    }

    public Long getMoney(String login) {
        var user = findByLogin(login);
        return user.getMoney() + user.getStocks().stream().map(Stock::getPrice).reduce(0L, Long::sum);
    }

    public void buy(String login, String company, Long count) {
        var user = findByLogin(login);
        var stock = validateBuy(user, company, count);
        user.setMoney(user.getMoney() - count * stock.getPrice());
        stock.setCount(stock.getCount() - count);
        var stocks = user.getStocks();
        addNewStocks(company, count, stocks, stock.getPrice());
        stockRepository.save(stock);
        userRepository.save(user);
    }

    private Stock validateBuy(User user, String company, Long count) {
        var stock = stockService.findByCompany(company).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Компания не найдена")
        );
        var totalPrice = count * stock.getPrice();
        if (user.getMoney() < totalPrice) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно средств для покупки");
        }
        if (count > stock.getCount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У компании на текущий момент нет столько акций");
        }
        return stock;
    }

    private void addNewStocks(String company, Long count, List<Stock> stocks, long currentPrice) {
        var stockExists = false;
        for (var currentStock : stocks) {
            if (currentStock.getCompany().equals(company)) {
                currentStock.setCount(currentStock.getCount() + count);
                stockExists = true;
                break;
            }
        }
        if (!stockExists) {
            var newStock = new Stock();
            newStock.setCount(count);
            newStock.setCompany(company);
            newStock.setPrice(currentPrice);
            stocks.add(newStock);
        }
    }

    public void sell(String login, String company, Long count) {
        var user = findByLogin(login);
        var stock = stockService.findByCompany(company).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Компания не найдена")
        );
        var totalPrice = count * stock.getPrice();
        var stocks = user.getStocks();
        var stockExists = false;

        for (var currentStock : stocks) {
            if (currentStock.getCompany().equals(company)) {
                if (currentStock.getCount() < count) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У вас нет столько акций");
                }
                currentStock.setCount(currentStock.getCount() - count);
                stock.setCount(stock.getCount() + count);
                user.setMoney(user.getMoney() + totalPrice);
                stockExists = true;
            }
        }
        if (!stockExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Акции данной компании не найдены");
        }
        stockRepository.save(stock);
        userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден")
        );
    }
}
