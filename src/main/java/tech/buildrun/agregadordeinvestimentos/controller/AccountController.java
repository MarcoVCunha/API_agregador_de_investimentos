package tech.buildrun.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.AccountResponseDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.AccountStockResponseDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.AssociateAccountStockDto;
import tech.buildrun.agregadordeinvestimentos.controller.DTO.CreateAccountDto;
import tech.buildrun.agregadordeinvestimentos.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                              @RequestBody AssociateAccountStockDto dto) {
        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listAssociateStock(@PathVariable("accountId") String accountId){
        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }
}
