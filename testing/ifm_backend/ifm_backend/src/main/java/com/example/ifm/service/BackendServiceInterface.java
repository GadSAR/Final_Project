package com.example.ifm.service;

import com.example.ifm.model.Account;
import com.example.ifm.model.Obd2Data;

import java.util.List;
import java.util.Optional;

public interface BackendServiceInterface {
    List<Obd2Data> getAllObd2Data();
    Obd2Data saveObd2Data(Obd2Data obd2Data);

    Optional<Obd2Data> getObd2DataById(Long id);

    Obd2Data updateObd2Data(Long id, Obd2Data obd2Data);

    List<Account> getAllAccounts();
    Account saveAccount(Account account);

    Optional<Account> getAccountById(Long id);

    Account updateAccount(Long id, Account account);

    void deleteStudent(Long id);
}
