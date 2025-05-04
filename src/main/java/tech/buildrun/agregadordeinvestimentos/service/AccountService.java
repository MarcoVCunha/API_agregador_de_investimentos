package tech.buildrun.agregadordeinvestimentos.service;


import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.AccountStockResponseDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.AssociateAccountStockDto;
import tech.buildrun.agregadordeinvestimentos.entity.AccountStock;
import tech.buildrun.agregadordeinvestimentos.entity.AccountStockId;
import tech.buildrun.agregadordeinvestimentos.repository.AccountRepository;
import tech.buildrun.agregadordeinvestimentos.repository.AccountStockRepository;
import tech.buildrun.agregadordeinvestimentos.repository.StockRepository;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO -> entity

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );

        accountStockRepository.save(entity);

    }

    @Transactional
    public List<AccountStockResponseDto> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as ->
                        new AccountStockResponseDto(as.getStock().getStockId(),
                                as.getQuantity(), 0.0))
                .toList();
    }
}
