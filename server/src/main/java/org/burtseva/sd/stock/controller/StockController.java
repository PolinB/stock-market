package org.burtseva.sd.stock.controller;

import org.burtseva.sd.stock.model.Stock;
import org.burtseva.sd.stock.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public Stock add(@RequestBody Stock stock) {
        return stockService.add(stock);
    }

    @GetMapping
    public List<Stock> allStocks() {
        return stockService.list();
    }

    @PutMapping("/{company}")
    public Stock updatePrice(@PathVariable String company, @RequestParam Long price) {
        return stockService.updatePrice(company, price);
    }
}
