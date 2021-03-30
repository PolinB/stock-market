package org.burtseva.sd.stock.controller;

import org.burtseva.sd.stock.model.Stock;
import org.burtseva.sd.stock.model.User;
import org.burtseva.sd.stock.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User register(@RequestParam String login) {
        return userService.register(login);
    }

    @GetMapping("/{login}")
    public User authorize(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @PutMapping("/buy/{login}")
    public void buy(@PathVariable String login, @RequestParam String company, @RequestParam Long count) {
        userService.buy(login, company, count);
    }

    @PutMapping("/sell/{login}")
    public void sell(@PathVariable String login, @RequestParam String company, @RequestParam Long count) {
        userService.sell(login, company, count);
    }

    @PutMapping("/money/add/{login}")
    public void addMoney(@PathVariable String login, @RequestParam Long value) {
        userService.addMoney(login, value);
    }

    @GetMapping("/stocks/{login}")
    public List<Stock> getStocks(@PathVariable String login) {
        return userService.getStocks(login);
    }

    @GetMapping("/money/{login}")
    public Long getMoney(@PathVariable String login) {
        return userService.getMoney(login);
    }
}
