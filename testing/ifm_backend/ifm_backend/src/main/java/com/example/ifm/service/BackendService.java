package com.example.ifm.service;

import com.example.ifm.model.Account;
import com.example.ifm.model.Obd2Data;
import com.example.ifm.repository.AccountRepository;
import com.example.ifm.repository.Obd2DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackendService implements BackendServiceInterface {

    Obd2DataRepository obd2DataRepository;
    AccountRepository accountRepository;

    @Autowired
    public BackendService(Obd2DataRepository obd2DataRepository) {

        this.obd2DataRepository = obd2DataRepository;
    }

    @Override
    public List<Obd2Data> getAllObd2Data() {
        // return sorted
       // return employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<Obd2Data> obd2Data = obd2DataRepository.findAll();
        // sort the list by lastname
      //  if (employees.size() > 0)
    //        Sort.by(Sort.DEFAULT_DIRECTION, employees.get(0).getLastName());
        return obd2Data;

    }

    @Override
    public Obd2Data saveObd2Data(Obd2Data obd2Data) {
        return obd2DataRepository.save(obd2Data);
    }

    @Override
    public Optional<Obd2Data> getObd2DataById(Long id) {
        return obd2DataRepository.findById(id);
    }

    @Override
    public Obd2Data updateObd2Data(Long id, Obd2Data obd2Data) {
    }

    @Override
    public List<Account> getAllAccounts() {
        return null;
    }

    @Override
    public Account saveAccount(Account account) {
        return null;
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return Optional.empty();
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Optional<Account> accountToFind = getAccountById(id);
        if (!accountToFind.isEmpty()) {
            accountToFind.get().setFirstName(Account.getFirstName());
            accountToFind.get().setLastName(Account.getLastName());
            accountToFind.get().setEmail(Account.getEmail());
            return AccountRepository.save(accountToFind.get());

        }
        else return null;
    }

    @Override
    public void deleteStudent(Long id) {
        obd2DataRepository.deleteById(id);
    }

}
