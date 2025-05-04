package tech.buildrun.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.CreateStockDto;
import tech.buildrun.agregadordeinvestimentos.entity.Stock;
import tech.buildrun.agregadordeinvestimentos.repository.StockRepository;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        // DTO -> entity
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);

    }
}
