package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    public Account validateAccount(Account account) {
        //Validate username and password 
        String username = account.getUsername();
        String password = account.getPassword();
        Account userAccount = getAccountByUsername(username);

        if (userAccount == null || !password.equals(userAccount.getPassword())) {
            return null;
        }
        return userAccount;

    }

    public Account addAccount(Account account) {
        //Validate username and password are correct length, not empty and not duplicated
        String username = account.getUsername();
        String password = account.getPassword();
        

        if (username == null || username.isEmpty() || password.length() < 4) {
            return null;
        }

        Account accountDuplicate = getAccountByUsername(username);
        if (accountDuplicate != null) {
            return null;
        }

        Account addedAccount = accountDAO.insertAccount(account);
        return addedAccount;
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccountByID(int account_id) {
        return accountDAO.getAccountByID(account_id);
    }
    
}
